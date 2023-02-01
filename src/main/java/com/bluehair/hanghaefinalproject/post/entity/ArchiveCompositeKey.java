package com.bluehair.hanghaefinalproject.post.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class ArchiveCompositeKey implements Serializable {
    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long postId;

    public ArchiveCompositeKey(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
