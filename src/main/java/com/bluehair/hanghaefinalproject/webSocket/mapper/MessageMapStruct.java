package com.bluehair.hanghaefinalproject.webSocket.mapper;

import com.bluehair.hanghaefinalproject.webSocket.dto.service.SaveMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapStruct {
    MessageMapStruct MESSAGE_MAPPER = Mappers.getMapper(MessageMapStruct.class);
    ChatMessage SaveMessageDtoToMessage(SaveMessageDto saveMessageDto);
}
