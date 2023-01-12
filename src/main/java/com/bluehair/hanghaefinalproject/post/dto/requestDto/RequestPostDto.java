package com.bluehair.hanghaefinalproject.post.dto.requestDto;

import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import lombok.Getter;

@Getter
public class RequestPostDto {
    private String title;
    private String contents;
    private String postImg;
    private String lyrics;
    private String tag;

    public PostDto toPostDto() {
        return PostDto.builder()
                .title(title)
                .contents(contents)
                .lyrics(lyrics)
                .postImg(postImg)
                .tag(tag)
                .build();
    }
}
