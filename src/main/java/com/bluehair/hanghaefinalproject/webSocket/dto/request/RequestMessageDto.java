package com.bluehair.hanghaefinalproject.webSocket.dto.request;

import com.bluehair.hanghaefinalproject.webSocket.dto.service.MessageDto;
import lombok.Getter;

@Getter
public class RequestMessageDto {

    private String message;

    private String nickname;
    private String date;
    private String time;

    public MessageDto toMessageDto() {
        return MessageDto.builder()
                .message(message)
                .date(date)
                .time(time)
                .build();
    }
}
