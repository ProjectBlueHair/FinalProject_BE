package com.bluehair.hanghaefinalproject.member.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class FollowCompositeKey implements Serializable {
    @Column(nullable = false)
    private Long memberId;
    @Column(nullable = false)
    private Long myFollowingId;

    public FollowCompositeKey(Long memberId, Long myFollowingId) {
        this.memberId = memberId;
        this.myFollowingId = myFollowingId;
    }
}
