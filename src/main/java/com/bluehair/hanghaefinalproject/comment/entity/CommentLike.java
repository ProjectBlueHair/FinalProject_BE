package com.bluehair.hanghaefinalproject.comment.entity;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
public class CommentLike{
    @EmbeddedId
    private CommentLikeCompositeKey id;
    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public CommentLike(CommentLikeCompositeKey id, Member member, Comment comment) {
        this.id = id;
        this.member = member;
        this.comment = comment;
    }
}
