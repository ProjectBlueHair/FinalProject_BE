package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollaboRequestResponseDto {

    public String title;
    public String contents;
    public String musicPart;
    public String musicFile;

    @Builder
    public CollaboRequestResponseDto(CollaboRequest collaboRequest, Music music) {
        this.title = collaboRequest.getTitle();
        this.contents = collaboRequest.getContents();
        this.musicPart = music.getMusicPart();
        this.musicFile = music.getMusicFile();
    }
}
