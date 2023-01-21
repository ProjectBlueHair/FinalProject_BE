package com.bluehair.hanghaefinalproject.collaboRequest.controller;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestListForPostDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.RequestCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.ResponseCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.service.CollaboRequestService;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.music.service.MusicService;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.*;

@Tag(name = "CollaboRequest", description = "콜라보 리퀘스트 관련 API")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CollaboRequestController {

    private final CollaboRequestService collaboRequestService;
    private final MusicService musicService;

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 작성 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글"),
            @ApiResponse(responseCode = "4003", description = "유효하지 않은 음원")
    })
    @Operation(summary = "콜라보 리퀘스트 작성", description = "해당 Post에 대한 콜라보 리퀘스트 작성")
    @PostMapping("/api/post/{postid}/collabo")
    public ResponseEntity<SuccessResponse<Object>> collaboRequest(@PathVariable Long postid,
                                                                  @RequestPart(value = "jsonData") RequestCollaboRequestDto requestCollaboRequestDto,
                                                                  @Parameter(description = "WAV 및 2 Channel 오디오만 지원합니다.")
                                                                  @RequestPart(value = "musicFile") List<MultipartFile> musicFileList,
                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long collaboRequestId = collaboRequestService.collaboRequest(postid, requestCollaboRequestDto.tocollaboRequestDetailsDto(), customUserDetails.getMember());
        musicService.saveMusic(musicFileList, postid, requestCollaboRequestDto.getMusicPartList(), collaboRequestId);
        return SuccessResponse.toResponseEntity(COLLABO_REQUEST_SUCCESS, collaboRequestId);
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 작성 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글"),
            @ApiResponse(responseCode = "4003", description = "유효하지 않은 음원")
    })
    @Operation(summary = "게시물에 대한 첫 콜라보 리퀘스트 작성", description = "해당 Post에 대한 첫 콜라보 리퀘스트 작성")
    @PostMapping("/api/post/{postid}/collabo/first")
    public ResponseEntity<SuccessResponse<Object>> collaboRequestFirst(@PathVariable Long postid,
                                                                  @RequestPart(value = "jsonData") RequestCollaboRequestDto requestCollaboRequestDto,
                                                                  @Parameter(description = "WAV 및 2 Channel 오디오만 지원합니다.")
                                                                  @RequestPart(value = "musicFile") List<MultipartFile> musicFileList,
                                                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long collaboRequestId = collaboRequestService.collaboRequestFirst(postid, requestCollaboRequestDto.tocollaboRequestDetailsDto(), customUserDetails.getMember());
        musicService.saveMusic(musicFileList, postid, requestCollaboRequestDto.getMusicPartList(), collaboRequestId);
        return SuccessResponse.toResponseEntity(COLLABO_REQUEST_SUCCESS, collaboRequestId);
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 상세 조회 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글"),
            @ApiResponse(responseCode = "4042", description = "존재하지 않는 콜라보리퀘스트")
    })
    @Operation(summary = "콜라보리퀘스트 상세 조회")
    @GetMapping("/api/collabo/{collaborequestid}")
    public ResponseEntity<SuccessResponse<ResponseCollaboRequestDto>> getCollaboRequest(@PathVariable Long collaborequestid){

        return SuccessResponse.toResponseEntity(COLLABO_REQUEST,collaboRequestService.getCollaboRequest(collaborequestid));
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 목록 조회 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글"),
            @ApiResponse(responseCode = "4040", description = "존재하지 않는 회원")
    })
    @Operation(summary = "해당 게시글 관련 콜라보 전체 조회")
    @GetMapping("/api/post/{postid}/collabo")
    public ResponseEntity<SuccessResponse<List<CollaboRequestListForPostDto>>> getCollaboRequestList(@PathVariable Long postid, @AuthenticationPrincipal CustomUserDetails userDetails ){

        Member member = null;
        try {
            member = userDetails.getMember();
        }catch (NullPointerException e){
            log.info("비로그인 사용자 접근 : post-" +postid + "-collabo_List");
        }
        return SuccessResponse.toResponseEntity(COLLABO_LIST, collaboRequestService.getCollaboRequestList(postid, member));

    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 승인"),
            @ApiResponse(responseCode = "4042", description = "존재하지 않는 콜라보리퀘스트"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자")
    })
    @Operation(summary = "콜라보리퀘스트 승인")
    @PostMapping("/api/collabo/{collaborequestid}")
    public ResponseEntity<SuccessResponse<Object>> approveCollaboRequest(@PathVariable Long collaborequestid, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws UnsupportedAudioFileException, IOException {
        collaboRequestService.approveCollaboRequest(collaborequestid, customUserDetails.getMember());
        musicService.mixMusic(collaborequestid);
        return SuccessResponse.toResponseEntity(COLLABO_REQUEST_APPROVAL, null);
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 삭제 성공"),
            @ApiResponse(responseCode = "4042", description = "존재하지 않는 콜라보리퀘스트"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자"),
            @ApiResponse(responseCode = "4002", description = "이미 승인된 콜라보리퀘스트")
    })
    @Operation(summary = "미승인 콜라보리퀘스트 삭제")
    @DeleteMapping("/api/collabo/{collaborequestid}")
    public ResponseEntity<SuccessResponse<Object>> deleteCollaboRequest(@PathVariable Long collaborequestid, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        musicService.deleteMusicList(collaborequestid);
        collaboRequestService.deleteCollaboRequest(collaborequestid, customUserDetails.getMember());
        return SuccessResponse.toResponseEntity(COLLABO_REQUEST_DELETE, null);
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 수정 성공"),
            @ApiResponse(responseCode = "4042", description = "존재하지 않는 콜라보리퀘스트"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자"),
            @ApiResponse(responseCode = "4002", description = "이미 승인된 콜라보리퀘스트"),
            @ApiResponse(responseCode = "4003", description = "유효하지 않은 음원")
    })
    @Operation(summary = "미승인 콜라보리퀘스트 수정")
    @PutMapping("/api/collabo/{collaborequestid}")
    public ResponseEntity<SuccessResponse<Object>> updateCollaboRequest(@PathVariable Long collaborequestid,
                                                                        @RequestPart(value = "jsonData") RequestCollaboRequestDto requestCollaboRequestDto,
                                                                        @Parameter(description = "WAV 및 2 Channel 오디오만 지원합니다.")
                                                                        @RequestPart(value = "musicFile", required = false) List<MultipartFile> musicFileList,
                                                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        collaboRequestService.updateCollaboRequest(
                collaborequestid,
                requestCollaboRequestDto.tocollaboRequestDetailsDto(),
                customUserDetails.getMember());
        musicService.deleteMusicList(collaborequestid);
        musicService.updateMusic(collaborequestid, musicFileList, requestCollaboRequestDto.getMusicPartList());
        return SuccessResponse.toResponseEntity(COLLABO_REQUEST_UPDATE, null);
    }
}
