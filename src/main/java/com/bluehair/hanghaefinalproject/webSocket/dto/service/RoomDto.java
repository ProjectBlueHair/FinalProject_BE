package com.bluehair.hanghaefinalproject.webSocket.dto.service;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDto {

    private Long memberId1;
    private Long memberId2;

    @Builder
    public RoomDto(Long memberId1, Long memberId2){
        this.memberId1 = memberId1;
        this.memberId2 = memberId2;
    }
}
