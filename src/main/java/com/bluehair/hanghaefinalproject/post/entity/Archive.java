package com.bluehair.hanghaefinalproject.post.entity;

import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Archive extends Timestamped {
    @EmbeddedId
    private ArchiveCompositeKey id;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Archive(ArchiveCompositeKey id, Member member, Post post) {
        this.id = id;
        this.member = member;
        this.post = post;
    }
}
