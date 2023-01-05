package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.music.dto.SaveMusicDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
public class RequestCollaboRequestDto {

    public String title;
    public String contents;
    public String musicPart;
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
