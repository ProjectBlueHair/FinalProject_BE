package com.bluehair.hanghaefinalproject.like.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeDto {
    private Boolean isLiked;
    private long likeCount;

    public PostLikeDto(boolean postLiked, long likeCount) {
        this.isLiked = postLiked;
        this.likeCount = likeCount;
    }
}
