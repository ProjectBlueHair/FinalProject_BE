package com.bluehair.hanghaefinalproject.collaboRequest.dto.serviceDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema
@Getter
public class CollaboRequestDetailsDto {
    public String contents;

    @Builder
    public CollaboRequestDetailsDto (String contents) {
        this.contents = contents;
    }

}
