package com.bluehair.hanghaefinalproject.comment.controller;

import com.bluehair.hanghaefinalproject.comment.dto.requestDto.RequestCommentDto;
import com.bluehair.hanghaefinalproject.comment.service.CommentService;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.CREATE_COMMENT;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.UPDATE_COMMENT;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.DELETE_COMMENT;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.GET_COMMENT;

@Tag(name = "Post", description = "댓글 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Tag(name = "Comment")
    @Operation(summary = "댓글 작성", description = "댓글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다."),
            @ApiResponse(responseCode = "4044", description = "존재하지 않는 댓글입니다.")
    })
    @PostMapping(value = {"/comment/{postId}/{parentId}", "/comment/{postId}"})
    public ResponseEntity<SuccessResponse<Object>> createComment(@PathVariable Long postId,@PathVariable(required = false) Long parentId,
                                                              @RequestBody RequestCommentDto requestCommentDto, @AuthenticationPrincipal CustomUserDetails userDetails){

        commentService.createComment(postId,parentId,requestCommentDto.toCommentDto(), userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(CREATE_COMMENT,null);
    }
    @Tag(name = "Comment")
    @Operation(summary = "댓글 수정", description = "댓글 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자입니다."),
            @ApiResponse(responseCode = "4044", description = "존재하지 않는 댓글입니다.")
    })
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<SuccessResponse<Object>> updateComment(@PathVariable Long commentId, @RequestBody RequestCommentDto requestCommentDto,
                                                                 @AuthenticationPrincipal CustomUserDetails userDetails){

        commentService.updateComment(commentId,requestCommentDto.toCommentDto(), userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(UPDATE_COMMENT, null);
    }

    @Tag(name = "Comment")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "4031", description = "접근 권한이 없는 사용자입니다."),
            @ApiResponse(responseCode = "4044", description = "존재하지 않는 댓글입니다.")
    })
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<SuccessResponse<Object>> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails){

        commentService.deleteComment(commentId,userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(DELETE_COMMENT, null);
    }

    @Tag(name = "Comment")
    @Operation(summary = "댓글 조회", description = "댓글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "댓글 조회 성공"),
    })

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<SuccessResponse<Object>> getCommentList(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails){
        Member member = null;
        try {
            member = userDetails.getMember();
        }catch (NullPointerException e){
            log.info("비로그인 사용자 접근 : commentList");
        }
        return SuccessResponse.toResponseEntity(GET_COMMENT, commentService.getComment(postId, member));
    }

}
