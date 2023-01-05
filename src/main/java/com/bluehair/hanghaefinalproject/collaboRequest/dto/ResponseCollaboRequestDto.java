package com.bluehair.hanghaefinalproject.collaboRequest.dto;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.dto.ResponseMusicDto;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseCollaboRequestDto {

   private String title;
   private String contents;
   private String nickname;
   private LocalDateTime createdAt;
   private LocalDateTime modifiedAt;
   private Boolean activated;
   private List<ResponseMusicDto> musicDtoList;

    @Builder
    public ResponseCollaboRequestDto(CollaboRequest collaboRequest, List<ResponseMusicDto> musicDtoList) {
        this.title = collaboRequest.getTitle();
        this.contents = collaboRequest.getContents();
        this.nickname = collaboRequest.getNickname();
        this.createdAt = collaboRequest.getCreatedAt();
        this.modifiedAt = collaboRequest.getModifiedAt();
        this.activated = collaboRequest.getActivated();
        this.musicDtoList = musicDtoList;
    }
}
