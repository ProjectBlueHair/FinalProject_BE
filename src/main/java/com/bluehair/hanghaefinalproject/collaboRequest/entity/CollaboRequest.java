package com.bluehair.hanghaefinalproject.collaboRequest.entity;

import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class CollaboRequest extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String nickname;

    @Column(columnDefinition = "boolean default true")
    private Boolean activated;

    @Column(columnDefinition = "boolean default false")
    private Boolean approval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collaboRequest")
    private List<Music> musicList = new ArrayList<>();

    @Builder
    public CollaboRequest(String title, String contents, String nickname, Boolean activated, Boolean approval, Post post) {
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.activated = activated;
        this.approval = approval;
        this.post = post;
    }

    public void approve(Boolean approval) {
        this.approval = approval;
    }
}

