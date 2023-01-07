package com.bluehair.hanghaefinalproject.comment.controller;

import com.bluehair.hanghaefinalproject.comment.dto.requestDto.RequestCommentDto;
import com.bluehair.hanghaefinalproject.comment.service.CommentService;
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


import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.CREATE_COMMENT;

@Tag(name = "Post", description = "댓글 관련 API")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Tag(name = "Comment")
    @Operation(summary = "댓글 작성", description = "댓글 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "댓글 작성 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다."),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 댓글입니다.")
    })
    @PostMapping(value = {"/{postId}/{parentId}", "/{postId}"})
    public ResponseEntity<SuccessResponse<Object>> createPost(@PathVariable Long postId,@PathVariable(required = false) Long parentId,
                                                              @RequestBody RequestCommentDto requestCommentDto, @AuthenticationPrincipal CustomUserDetails userDetails){

        commentService.createComment(postId,parentId,requestCommentDto.toCommentDto(), userDetails.getMember().getNickname());

        return SuccessResponse.toResponseEntity(CREATE_COMMENT,null);
    }


}