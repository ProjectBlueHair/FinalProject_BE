package com.bluehair.hanghaefinalproject.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema
@Getter
public class RequestPostDto {
    private String title;
    private String contents;
    private String lyrics;
    private String musicFile;
    private String musicPart;
}
