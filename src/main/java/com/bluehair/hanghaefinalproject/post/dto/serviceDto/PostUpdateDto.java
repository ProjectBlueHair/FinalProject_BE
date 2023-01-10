package com.bluehair.hanghaefinalproject.post.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateDto {
    private String title;
    private String contents;
    private String lyrics;
    private String postImg;

    @Builder
    public PostUpdateDto(String title, String contents, String lyrics,
                   String postImg){
        this.title = title;
        this.contents = contents;
        this.lyrics = lyrics;
        this.postImg = postImg;
    }
}
