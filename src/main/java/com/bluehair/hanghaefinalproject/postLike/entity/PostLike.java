package com.bluehair.hanghaefinalproject.postLike.entity;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class PostLike {

    @EmbeddedId
    private PostLikeCompositeKey id;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @MapsId("postLikedId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post postLiked;

    public PostLike(PostLikeCompositeKey id, Member member, Post postLiked){
        this.id = id;
        this.member = member;
        this.postLiked = postLiked;
    }
}
