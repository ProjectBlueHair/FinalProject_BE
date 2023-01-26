package com.bluehair.hanghaefinalproject.sse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCountNotificationDto {

    private Long unreadNotificationCount;

    public ResponseCountNotificationDto(Long unreadNotificationCount) {
        this.unreadNotificationCount = unreadNotificationCount;
    }
}
