package com.bluehair.hanghaefinalproject.webSocket.controller;

import com.bluehair.hanghaefinalproject.webSocket.dto.request.RequestMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.dto.service.SaveMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    @MessageMapping("/chat/message/{roomId}") // app/chat/message/{roomId} 로 메세지를 보냄
    @SendTo("/topic/chat/room/{roomId}") // topic/chat/room/{roomId} 를 구독하고 있는 클라이언트에 return 함.
    public SaveMessageDto enter(@DestinationVariable Long roomId, RequestMessageDto requestMessageDto) {

        SaveMessageDto saveMessageDto = messageService.saveMessage(roomId,requestMessageDto.toMessageDto(), requestMessageDto.getNickname());

        return saveMessageDto;
    }
}
