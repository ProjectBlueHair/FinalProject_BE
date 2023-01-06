package com.bluehair.hanghaefinalproject.post.dto.requestDto;

import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public class RequestPostDto {
    private String title;
    private String contents;
    private String postImage;
    private String lyrics;
    private String musicFile;
    private String musicPart;

    public PostDto toPostDto() {
        return PostDto.builder()
                .title(title)
                .contents(contents)
                .lyrics(lyrics)
                .musicFile(musicFile)
                .musicPart(musicPart)
                .postImage(postImage)
                .build();
    }
}
