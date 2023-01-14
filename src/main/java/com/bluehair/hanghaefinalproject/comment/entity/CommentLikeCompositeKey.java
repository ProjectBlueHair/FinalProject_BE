package com.bluehair.hanghaefinalproject.comment.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@NoArgsConstructor
public class CommentLikeCompositeKey implements Serializable {
    @Column(nullable = false)
    private Long memberId;
    @Column(nullable = false)
    private Long commentId;

    public CommentLikeCompositeKey(Long memberId, Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
