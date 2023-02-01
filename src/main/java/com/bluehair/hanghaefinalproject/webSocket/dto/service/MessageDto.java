package com.bluehair.hanghaefinalproject.webSocket.dto.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageDto {

    private String message;
    private String date;
    private String time;
    @Builder
    public MessageDto(String message, String date, String time){
        this.message = message;
        this.date = date;
        this.time = time;
    }
}
