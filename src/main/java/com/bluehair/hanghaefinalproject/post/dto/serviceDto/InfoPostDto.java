package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
@Schema(description = "상세 조회 응답 Dto")
@Getter
public class InfoPostDto {

    private String title;
    private String contents;
    private String lyrics;
    private String musicFile;
    private String musicPart;
    private Long likeCount;
    private Long viewCount;
    // 태그 리스트 추가 구현 필요
    @Builder
    public InfoPostDto(Post post){
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.lyrics = post.getLyrics();
        this.musicFile = post.getMusicFile();
        this.musicPart = post.getMusicPart();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
    }

}
