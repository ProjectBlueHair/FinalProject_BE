package com.bluehair.hanghaefinalproject.comment.dto.serviceDto;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplyDto {

    private Long id;
    private String contents;
    private String nickname;
    private String profileImg;
    private Long parentsId;
    private String createdAt;
    private String modifiedAt;
    private Long likeCount;
    private Boolean isLiked;
    @Builder
    public ReplyDto(Comment comment, Boolean isLiked){
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.nickname = comment.getNickname();
        this.profileImg = comment.getProfileImg();
        this.parentsId = comment.getParentsId();
        this.createdAt = LocalDateTimeConverter.timeToString(comment.getCreatedAt());
        this.modifiedAt = LocalDateTimeConverter.timeToString(comment.getModifiedAt());
        this.likeCount = comment.getLikeCount();
        this.isLiked = isLiked;
    }

}
