package com.bluehair.hanghaefinalproject.webSocket.dto.service;

import lombok.Getter;

@Getter
public class SaveMessageDto {

    private Long roomId;
    private String nickname;
    private String profileImg;
    private String message;
    private String date;
    private String time;

    public SaveMessageDto(Long roomId, String message, String nickname, String profileImg, String date, String time) {
        this.roomId = roomId;
        this.message = message;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.date = date;
        this.time = time;
    }
}
