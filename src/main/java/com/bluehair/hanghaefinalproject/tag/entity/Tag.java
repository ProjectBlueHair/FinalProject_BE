package com.bluehair.hanghaefinalproject.tag.entity;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @Builder
    public Tag(String contents, Post post) {
        this.contents = contents;
        this.post = post;

        post.getTagList().add(this);
    }
}
