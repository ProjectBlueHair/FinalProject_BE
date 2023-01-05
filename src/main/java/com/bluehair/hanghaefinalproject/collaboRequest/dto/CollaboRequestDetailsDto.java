package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema
@Getter
public class CollaboRequestDetailsDto {
    public String title;
    public String contents;

    @Builder
    public CollaboRequestDetailsDto (String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}