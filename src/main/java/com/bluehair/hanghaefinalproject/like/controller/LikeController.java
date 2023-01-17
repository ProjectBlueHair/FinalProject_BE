package com.bluehair.hanghaefinalproject.like.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.like.dto.ResponsePostLikeDto;
import com.bluehair.hanghaefinalproject.like.service.LikeService;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.*;

@Tag(name = "Like", description = "Like 관련 API")
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    @Tag(name ="Like")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "좋아요/좋아요 취소 성공"),
            @ApiResponse(responseCode = "4041", description = "존재하지 않는 게시글입니다.")
    })
    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요 표시")
    @PostMapping("/api/post/like/{postid}")
    public ResponseEntity<SuccessResponse<ResponsePostLikeDto>> postLike(@PathVariable Long postid, @AuthenticationPrincipal CustomUserDetails userDetails){

        return SuccessResponse.toResponseEntity(POST_LIKE, likeService.postLike(postid, userDetails.getMember()));
    }

    @Tag(name = "Like")
    @Operation(summary = "댓글 좋아요", description = "댓글 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "댓글 좋아요 성공"),
            @ApiResponse(responseCode = "2000", description = "댓글 좋아요 취소"),
            @ApiResponse(responseCode = "4044", description = "존재하지 않는 댓글입니다.")
    })
    @PostMapping("/api/comment/like/{commentId}")
    public ResponseEntity<SuccessResponse<Object>> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails){

        boolean liked = likeService.likeComment(commentId, userDetails.getMember().getNickname());
        if (liked){
            return SuccessResponse.toResponseEntity(LIKE_COMMENT,null);
        }else{
            return SuccessResponse.toResponseEntity(UNLIKE_COMMENT,null);
        }
    }
}
