package com.bluehair.hanghaefinalproject.webSocket.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import com.bluehair.hanghaefinalproject.webSocket.dto.request.RequestMessageDto;
import com.bluehair.hanghaefinalproject.webSocket.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.MESSAGE_LIST;

@Tag(name = "메세지 전송", description = "메세지 전송 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;
    @Tag(name = "message")
    @Operation(summary = "메세지 전송", description = "메세지 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "메세지 전송 성공")
    })
    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/topic/chat/room/{roomId}")
    public ResponseEntity<SuccessResponse<Object>> enter(@DestinationVariable Long roomId, @RequestBody RequestMessageDto requestMessageDto, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return SuccessResponse.toResponseEntity(MESSAGE_LIST,messageService.saveMessage(roomId,requestMessageDto.toMessageDto(), userDetails.getMember().getNickname()));
    }
}
