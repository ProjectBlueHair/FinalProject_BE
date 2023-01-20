package com.bluehair.hanghaefinalproject.sse.dto;

import com.bluehair.hanghaefinalproject.common.service.LocalDateTimeConverter;
import com.bluehair.hanghaefinalproject.sse.entity.Notification;
import com.bluehair.hanghaefinalproject.sse.entity.RedirectionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "알림 Dto")
@Getter
@Setter
public class ResponseNotificationDto {
    private Long id;
    private String content;
    private RedirectionType type;
    private Long typeId;
    private String sender;
    private String senderImg;
    private Boolean isRead;
    @Schema(description = "알림 발송 시간", example = "시간")
    private String createdAt;

    @Builder
    public ResponseNotificationDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.type = notification.getUrl().getType();
        this.typeId = notification.getUrl().getTypeId();
        this.sender = notification.getSender().getSender();
        this.senderImg = notification.getSender().getSenderImg();
        this.isRead = notification.getIsRead();
        this.createdAt = LocalDateTimeConverter.timeToString(notification.getCreatedAt());
    }

}
