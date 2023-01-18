package com.bluehair.hanghaefinalproject.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentLikeDto {
    private Boolean isLiked;
    private long likeCount;

    public CommentLikeDto(Boolean isLiked, long likeCount) {
        this.isLiked = isLiked;
        this.likeCount = likeCount;
    }
}
