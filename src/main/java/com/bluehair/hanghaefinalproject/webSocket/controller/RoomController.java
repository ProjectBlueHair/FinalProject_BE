package com.bluehair.hanghaefinalproject.webSocket.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import  com.bluehair.hanghaefinalproject.webSocket.service.*;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.ROOM_LIST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.ROOM_CREATE;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.MESSAGE_LIST;

@Tag(name = "Room", description = "채팅방 관련 API")
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService chatService;

    @Tag(name = "Room")
    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "채팅방 목록 조회 성공")
    })
    @GetMapping("/rooms")
    public ResponseEntity<SuccessResponse<Object>> room(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return SuccessResponse.toResponseEntity(ROOM_LIST,chatService.findAllRoom(userDetails.getMember().getNickname()));
    }
    @Tag(name = "Room")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "채팅방 개설 성공"),
            @ApiResponse(responseCode = "4040", description = "존재하지 않는 회원")
    })
    // 채팅방 생성
    @PostMapping("/room/{nickname}")
    public ResponseEntity<SuccessResponse<Object>> createRoom(@PathVariable String nickname, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return SuccessResponse.toResponseEntity(ROOM_CREATE,chatService.createRoom(nickname, userDetails.getMember().getNickname()));
    }
    @Tag(name = "Room")
    @Operation(summary = "메세지 목록 조회", description = "메세지 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "메세지 목록 조회 성공"),
            @ApiResponse(responseCode = "4040", description = "존재하지 않는 회원")
    })
    // 메세지 조회
    @GetMapping("/room/{roomId}")
    public ResponseEntity<SuccessResponse<Object>> roomDetail(@PathVariable Long roomId) {
        return SuccessResponse.toResponseEntity(MESSAGE_LIST,chatService.entranceRoom(roomId));
    }

}
