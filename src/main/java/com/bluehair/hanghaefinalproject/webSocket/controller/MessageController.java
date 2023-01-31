package com.bluehair.hanghaefinalproject.webSocket.controller;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import com.bluehair.hanghaefinalproject.webSocket.dto.request.RequestMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메세지 전송", description = "메세지 전송 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;
    @Tag(name = "message")
    @Operation(summary = "메세지 전송", description = "메세지 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "메세지 전송 성공")
    })
    @MessageMapping("/chat/message")
    public void enter(@RequestBody RequestMessageDto requestMessageDto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        messageService.saveMessage(requestMessageDto.toMessageDto(), userDetails.getMember().getNickname());
        sendingOperations.convertAndSend("/topic/chat/room/"+requestMessageDto.getRoomId(),requestMessageDto.getMessage());


    }
}
