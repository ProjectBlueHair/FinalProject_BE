package com.bluehair.hanghaefinalproject.postLike.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class PostLikeCompositeKey implements Serializable {

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long postLikedId;

    public PostLikeCompositeKey(Long memberId, Long postLikedId){
        this.memberId = memberId;
        this.postLikedId = postLikedId;
    }
}
