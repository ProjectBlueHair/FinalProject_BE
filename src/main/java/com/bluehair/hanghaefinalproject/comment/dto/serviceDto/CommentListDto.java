package com.bluehair.hanghaefinalproject.comment.dto.serviceDto;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "댓글 리스트 조회 Dto")
@Getter
public class CommentListDto {

    @Schema(description = "댓글 번호", example = "댓글 번호")
    private Long id;
    @Schema(description = "유저 프로필 이미지", example = "유저 프로필 이미지")
    private String profileImg;
    @Schema(description = "유저 닉네임", example = "유저 닉네임")
    private String nickname;
    @Schema(description = "댓글 내용", example = "댓글 내용")
    private String contents;
    @Schema(description = "댓글 부모 번호", example = "댓글 부모 번호")
    private Long parentsId;
    @Schema(description = "작성시간", example = "작성시간")
    private String createdAt;
    @Schema(description = "수정시간", example = "수정시간")
    private String modifiedAt;
    @Schema(description = "좋아요 갯수", example = "좋아요 갯수")
    private Long likeCount;
    @Schema(description = "좋아요 여부", example = "좋아요 여부")
    private boolean isLiked;
    @Schema(description = "대댓글 리스트", example = "대댓글 리스트")
    private List<ReplyDto> replyList;

    @Builder
    public CommentListDto(Comment comment, boolean isLiked, List<ReplyDto> replyList){
        this.id = comment.getId();
        this.profileImg = comment.getProfileImg();
        this.nickname = comment.getNickname();
        this.contents = comment.getContents();
        this.parentsId = comment.getParentsId();
        this.createdAt = LocalDateTimeConverter.timeToString(comment.getCreatedAt());
        this.modifiedAt = LocalDateTimeConverter.timeToString(comment.getModifiedAt());
        this.likeCount = comment.getLikeCount();
        this.isLiked = isLiked;
        this.replyList = replyList;
    }
}
