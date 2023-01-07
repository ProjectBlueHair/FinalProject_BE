package com.bluehair.hanghaefinalproject.collaboRequest.controller;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.CollaboRequestListForPostDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.RequestCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.dto.ResponseCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.service.CollaboRequestService;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.*;

@Tag(name = "CollaboRequest", description = "콜라보 리퀘스트 관련 API")
@RequiredArgsConstructor
@RestController
public class CollaboRequestController {

    private final CollaboRequestService collaboRequestService;

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 작성 성공"),
            @ApiResponse(responseCode = "4043", description = "존재하지 않는 게시글")
    })
    @Operation(summary = "콜라보 리퀘스트 작성", description = "특정 Post에 대한 콜라보 리퀘스트 작성")
    @PostMapping("/api/post/{postid}/collabo")
    public ResponseEntity<SuccessResponse<Object>> collaboRequest(@PathVariable Long postid, @RequestBody RequestCollaboRequestDto requestCollaboRequestDto, @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails){
        collaboRequestService.collaboRequest(postid, requestCollaboRequestDto.tocollaboRequestDetailsDto(), requestCollaboRequestDto.tosaveMusicDto(), customUserDetails.getMember());

        return SuccessResponse.toResponseEntity(COLLABO_REQUEST_SUCCESS, null);
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 상세 조회 성공"),
            @ApiResponse(responseCode = "4043", description = "존재하지 않는 게시글"),
            @ApiResponse(responseCode = "4044", description = "존재하지 않는 콜라보리퀘스트")
    })
    @Operation(summary = "콜라보리퀘스트 상세 조회")
    @GetMapping("/api/collabo/{collaborequestid}")
    public ResponseEntity<SuccessResponse<ResponseCollaboRequestDto>> getCollaboRequest(@PathVariable Long collaborequestid){

        return SuccessResponse.toResponseEntity(COLLABO_REQUEST,collaboRequestService.getCollaboRequest(collaborequestid));
    }

    @Tag(name = "CollaboRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "콜라보리퀘스트 목록 조회 성공"),
            @ApiResponse(responseCode = "4043", description = "존재하지 않는 게시글"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 회원")
    })
    @Operation(summary = "해당 게시글 관련 콜라보 전체 조회")
    @GetMapping("/api/post/{postid}/collabo")
    public ResponseEntity<SuccessResponse<List<CollaboRequestListForPostDto>>> getCollaboRequestList(@PathVariable Long postid){
        return SuccessResponse.toResponseEntity(COLLABO_LIST, collaboRequestService.getCollaboRequestList(postid));
    }

}
