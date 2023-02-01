package com.bluehair.hanghaefinalproject.webSocket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "채팅방 생성 응답 Dto")
@Getter
public class RoomIdDto {

    @Schema(description = "채팅방 번호", example ="채팅방 번호")
    private Long roomId;

    public RoomIdDto(Long roomId){
        this.roomId = roomId;
    }
}
