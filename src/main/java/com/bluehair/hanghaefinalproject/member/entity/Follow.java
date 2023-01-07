package com.bluehair.hanghaefinalproject.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Follow {
    @EmbeddedId
    private FollowCompositeKey id;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @MapsId("myFollowingId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member myFollowing;

    public Follow(FollowCompositeKey id, Member member, Member myFollowing) {
        this.id = id;
        this.member = member;
        this.myFollowing = myFollowing;
    }

}
