package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.music.dto.SaveMusicDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "콜라보 리퀘스트 요청 Dto")
@Getter
public class RequestCollaboRequestDto {
    @Schema(description = "콜라보 리퀘스트 제목", required = true, example = "콜라보 요청합니다.")
    public String title;
    @Schema(description = "콜라보 리퀘스트 내용", required = true, example = "콜라보하고 싶습니다.")
    public String contents;
    @Schema(description = "콜라보 리퀘스트 음악파트", required = true, example = "보컬")
    public String musicPart;
    @Schema(description = "콜라보 리퀘스트 음악 파일", required = true, example = "Music File from S3")
    public String musicFile;

    public CollaboRequestDetailsDto tocollaboRequestDetailsDto(){
        return CollaboRequestDetailsDto.builder()
                .title(title)
                .contents(contents)
                .build();
    }

    public SaveMusicDto tosaveMusicDto(){
        return SaveMusicDto.builder()
                .musicPart(musicPart)
                .musicFile(musicFile)
                .build();
    }
}
