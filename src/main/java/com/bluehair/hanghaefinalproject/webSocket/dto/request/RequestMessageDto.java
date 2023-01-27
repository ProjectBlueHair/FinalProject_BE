package com.bluehair.hanghaefinalproject.webSocket.dto.request;

import com.bluehair.hanghaefinalproject.webSocket.dto.service.MessageDto;
import lombok.Getter;

@Getter
public class RequestMessageDto {

    private Long roomId;
    private String message;

    public MessageDto toMessageDto() {
        return MessageDto.builder()
                .roomId(roomId)
                .message(message)
                .build();
    }
}
