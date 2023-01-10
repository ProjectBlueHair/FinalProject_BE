package com.bluehair.hanghaefinalproject.post.dto.requestDto;

import com.bluehair.hanghaefinalproject.post.dto.serviceDto.PostUpdateDto;
import lombok.Getter;

@Getter
public class RequestUpdatePostDto {

    private String title;
    private String contents;
    private String lyrics;
    private String postImg;

    public PostUpdateDto toPostUpdateDto() {
        return PostUpdateDto.builder()
                .title(title)
                .contents(contents)
                .lyrics(lyrics)
                .postImg(postImg)
                .build();
    }
}
