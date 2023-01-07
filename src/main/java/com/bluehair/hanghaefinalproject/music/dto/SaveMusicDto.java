package com.bluehair.hanghaefinalproject.music.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SaveMusicDto {
    private List<MusicDto> musicDtoList;

    @Builder
    public SaveMusicDto(List<MusicDto> musicDtoList) {
        this.musicDtoList = musicDtoList;
    }
}
