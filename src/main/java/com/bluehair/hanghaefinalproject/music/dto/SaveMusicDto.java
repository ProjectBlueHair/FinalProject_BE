package com.bluehair.hanghaefinalproject.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SaveMusicDto {
    private String musicFile;
    private String musicPart;

    @Builder
    public SaveMusicDto(String musicFile, String musicPart) {
        this.musicFile = musicFile;
        this.musicPart = musicPart;
    }
}
