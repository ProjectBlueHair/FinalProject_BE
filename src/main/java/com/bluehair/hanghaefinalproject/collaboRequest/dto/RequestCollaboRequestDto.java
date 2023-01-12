package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "콜라보 리퀘스트 요청 Dto")
@Getter
public class RequestCollaboRequestDto {
    @Schema(description = "콜라보 리퀘스트 제목", required = true, example = "콜라보 요청합니다.")
    public String title;
    @Schema(description = "콜라보 리퀘스트 내용", required = true, example = "콜라보하고 싶습니다.")
    public String contents;
    @Schema(description = "콜라보 리퀘스트 음악 리스트", required = true)
    public List<String> musicPartList;

    public CollaboRequestDetailsDto tocollaboRequestDetailsDto(){
        return CollaboRequestDetailsDto.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}
