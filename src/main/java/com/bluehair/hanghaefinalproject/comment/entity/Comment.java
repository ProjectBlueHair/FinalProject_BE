package com.bluehair.hanghaefinalproject.comment.entity;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImg;

    @Column(nullable = false)
    private String contents;

    @Column
    private Long parentsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public Comment(Long parentsId, String nickname, String profileImg, String contents, Post post){
        this.parentsId = parentsId;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.contents = contents;
        this.post = post;
    }
}
