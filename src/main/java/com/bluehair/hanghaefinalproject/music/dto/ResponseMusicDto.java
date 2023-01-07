package com.bluehair.hanghaefinalproject.music.dto;

import com.bluehair.hanghaefinalproject.music.entity.Music;
import lombok.Getter;

@Getter
public class ResponseMusicDto {
    private String musicFile;
    private String musicPart;

    public ResponseMusicDto(Music music) {
        this.musicFile = music.getMusicFile();
        this.musicPart = music.getMusicPart();
    }
}
