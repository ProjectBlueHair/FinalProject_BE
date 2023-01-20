package com.bluehair.hanghaefinalproject.post.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import com.bluehair.hanghaefinalproject.post.dto.requestDto.RequestPostDto;
import com.bluehair.hanghaefinalproject.post.dto.requestDto.RequestUpdatePostDto;
import com.bluehair.hanghaefinalproject.post.service.PostService;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.MY_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.INFO_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.SEARCH_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.MAIN_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.CREATE_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.UPDATE_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.MUSIC_POST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.DELETE_POST;


@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @Tag(name = "Post")
    @Operation(summary = "게시글 작성", description = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "게시글 작성 성공")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<Object>> createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        return SuccessResponse.toResponseEntity(CREATE_POST,postService.createPost(requestPostDto.toPostDto(), userDetails.getMember().getNickname()));
    }
    @Tag(name = "Post")
    @Operation(summary = "게시글 수정", description = "게시글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자입니다."),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다.")
    })
    @PutMapping("/{postId}")
    public ResponseEntity<SuccessResponse<Object>> updatePost(@PathVariable Long postId,@RequestBody RequestUpdatePostDto requestPostDto,
                                                              @AuthenticationPrincipal CustomUserDetails userDetails){

        postService.updatePost(postId, requestPostDto.toPostUpdateDto(), userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(UPDATE_POST,null);
    }

    @Tag(name = "Post")
    @Operation(summary = "게시글 조회", description = "특정 게시글 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "상세 게시글 조회 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다.")
    })
    @GetMapping("/details/{postid}")
    public ResponseEntity<SuccessResponse<Object>> infoPost(@PathVariable Long postid, @AuthenticationPrincipal CustomUserDetails userDetails){
        Member member = null;
        try {
            member = userDetails.getMember();
        }catch (NullPointerException e){
            log.info("비로그인 사용자 접근 : infoPost");
        }
        return SuccessResponse.toResponseEntity(INFO_POST,postService.infoPost(postid, member));
    }

    @Tag(name = "Post")
    @Operation(summary = "게시글 조회", description = "게시글 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "전체 게시글 조회 성공")
    })
    @GetMapping(value = {""})
    public ResponseEntity<SuccessResponse<Object>> mainPost(Pageable pageable, @RequestParam(name = "search", required = false) String search){
        if(search != null){
            return SuccessResponse.toResponseEntity(SEARCH_POST,postService.mainPost(pageable,search));
        }else{
            return SuccessResponse.toResponseEntity(MAIN_POST,postService.mainPost(pageable, search));
        }
    }
    @Tag(name = "Post")
    @Operation(summary = "작성한 게시글 조회", description = "작성한 게시글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "작성한 게시글 조회 성공")
    })
    @GetMapping("/my-post")
    public ResponseEntity<SuccessResponse<Object>> myPost(Pageable pageable, @AuthenticationPrincipal CustomUserDetails userDetails){

        return SuccessResponse.toResponseEntity(MY_POST, postService.myPost(pageable, userDetails.getMember().getNickname()));

    }

    @Tag(name = "Post")
    @Operation(summary = "상세 게시글 페이지 음악 조회", description = "상세 게시글 페이지 음악 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "게시글 전체 음악 조회 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다.")
    })
    @GetMapping("/{postId}/music")
    public ResponseEntity<SuccessResponse<List<ResponseMusicDto>>> musicPost(@PathVariable Long postId){
        return SuccessResponse.toResponseEntity(MUSIC_POST, postService.musicPost(postId));
    }
    @Tag(name="Post")
    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자입니다."),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다.")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse<List<ResponseMusicDto>>> deletePost(@PathVariable Long postId,@AuthenticationPrincipal CustomUserDetails userDetails){
        postService.deletePost(postId, userDetails.getMember().getNickname());
        return SuccessResponse.toResponseEntity(DELETE_POST, null);
    }
}
