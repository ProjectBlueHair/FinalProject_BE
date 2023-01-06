package com.bluehair.hanghaefinalproject.post.entity;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import com.bluehair.hanghaefinalproject.tag.entity.Tag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column
    private String lyrics;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String postImage;
    @Column
    private String musicFile;
    @Column
    private String musicPart;

    @ColumnDefault("0")
    private Long viewCount;

    @ColumnDefault("0")
    private Long likeCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<CollaboRequest> collaboRequestList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post (String title, String contents, String nickname, String lyrics,
                 String postImage, String musicFile, String musicPart){
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.lyrics = lyrics;
        this.musicFile = musicFile;
        this.musicPart = musicPart;
        this.postImage = postImage;
    }

}
