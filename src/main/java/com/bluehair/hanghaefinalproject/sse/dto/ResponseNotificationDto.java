package com.bluehair.hanghaefinalproject.sse.dto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import com.bluehair.hanghaefinalproject.sse.entity.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "알림 Dto")
@Getter
@Setter
public class ResponseNotificationDto {
    private Long id;
    private String content;
    private String url;
    private Boolean isRead;
    @Schema(description = "알림 발송 시간", example = "시간")
    private String createdAt;

    @Builder
    public ResponseNotificationDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.url = notification.getUrl();
        this.isRead = notification.getIsRead();
        this.createdAt = LocalDateTimeConverter.timeToString(notification.getCreatedAt());
    }

}
