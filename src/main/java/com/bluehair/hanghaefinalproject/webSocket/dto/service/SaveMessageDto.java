package com.bluehair.hanghaefinalproject.webSocket.dto.service;

import lombok.Getter;

@Getter
public class SaveMessageDto {

    private Long roomId;
    private String nickname;
    private String profileImg;
    private String message;

    public SaveMessageDto(Long roomId, String message, String nickname, String profileImg) {
        this.roomId = roomId;
        this.message = message;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }
}
