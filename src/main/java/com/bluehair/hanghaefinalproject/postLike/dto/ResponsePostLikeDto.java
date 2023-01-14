package com.bluehair.hanghaefinalproject.postLike.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponsePostLikeDto {
    private boolean postLiked;
    private long likeCount;

    public ResponsePostLikeDto(boolean postLiked, long likeCount) {
        this.postLiked = postLiked;
        this.likeCount = likeCount;
    }
}
