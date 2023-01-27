package com.bluehair.hanghaefinalproject.webSocket.dto.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageDto {

    private Long roomId;
    private String message;

    @Builder
    public MessageDto(Long roomId, String message){
        this.roomId = roomId;
        this.message = message;
    }
}
