package com.bluehair.hanghaefinalproject.music.dto;

import com.bluehair.hanghaefinalproject.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MusicDto {
    private String musicFile;
    private String musicPart;

    @Builder
    public MusicDto(Music music) {
        this.musicFile = music.getMusicFile();
        this.musicPart = music.getMusicPart();
    }
}
