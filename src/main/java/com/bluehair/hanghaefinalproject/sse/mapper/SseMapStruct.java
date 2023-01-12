package com.bluehair.hanghaefinalproject.sse.mapper;

import com.bluehair.hanghaefinalproject.sse.dto.ResponseNotificationDto;
import com.bluehair.hanghaefinalproject.sse.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SseMapStruct {
    SseMapStruct SSE_MAPPER = Mappers.getMapper(SseMapStruct.class);

    ResponseNotificationDto NotificationtoResponseNotificationDto(Notification notification);

}
