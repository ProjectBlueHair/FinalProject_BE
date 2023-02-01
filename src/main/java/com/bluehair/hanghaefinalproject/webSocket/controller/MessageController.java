package com.bluehair.hanghaefinalproject.webSocket.controller;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import com.bluehair.hanghaefinalproject.webSocket.dto.request.RequestMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.dto.service.SaveMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    
    @MessageMapping("/chat/message/{roomId}") // app/chat/message/{roomId} 로 메세지를 보냄
    @SendTo("/topic/chat/room/{roomId}") // topic/chat/room/{roomId} 를 구독하고 있는 클라이언트에 return 함.
    public SaveMessageDto enter(@DestinationVariable Long roomId, @RequestBody RequestMessageDto requestMessageDto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        SaveMessageDto saveMessageDto = messageService.saveMessage(roomId,requestMessageDto.toMessageDto(), userDetails.getMember().getNickname());

        return saveMessageDto;
    }
}
