package com.bluehair.hanghaefinalproject.post.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
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

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.*;


@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Tag(name = "Post")
    @Operation(summary = "게시글 작성", description = "게시글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "게시글 작성 성공")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<Object>> createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal CustomUserDetails userDetails){

        postService.createPost(requestPostDto.toPostDto(), userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(CREATE_POST,null);
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
    @GetMapping("/{postid}")
    public ResponseEntity<SuccessResponse<Object>> infoPost(@PathVariable Long postid){

        return SuccessResponse.toResponseEntity(INFO_POST,postService.infoPost(postid));
    }

    @Tag(name = "Post")
    @Operation(summary = "게시글 조회", description = "게시글 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "전체 게시글 조회 성공")
    })
    @GetMapping("")
    public ResponseEntity<SuccessResponse<Object>> mainPost(Pageable pageable){

        return SuccessResponse.toResponseEntity(MAIN_POST,postService.mainPost(pageable));
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
}
