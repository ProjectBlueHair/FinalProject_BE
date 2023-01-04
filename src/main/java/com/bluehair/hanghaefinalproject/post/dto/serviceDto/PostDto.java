package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {
    private String title;
    private String contents;
    private String lyrics;
    private String musicFile;
    private String musicPart;
    @Builder
    public PostDto(String title, String contents, String lyrics,
                String musicFile, String musicPart){
        this.title = title;
        this.contents = contents;
        this.lyrics = lyrics;
        this.musicFile = musicFile;
        this.musicPart = musicPart;
    }
}
