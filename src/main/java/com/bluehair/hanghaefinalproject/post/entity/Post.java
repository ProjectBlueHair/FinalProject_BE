package com.bluehair.hanghaefinalproject.post.entity;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import com.bluehair.hanghaefinalproject.tag.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
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

    @Column
    private String musicFile;

    @Column(columnDefinition = "bigint default 0")
    private Long viewCount;

    @Column(columnDefinition = "bigint default 0")
    private Long likeCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<CollaboRequest> collaboRequestList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Tag> tagList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

}
