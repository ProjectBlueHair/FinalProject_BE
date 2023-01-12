package com.bluehair.hanghaefinalproject.sse.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import com.bluehair.hanghaefinalproject.sse.dto.ResponseNotificationDto;
import com.bluehair.hanghaefinalproject.sse.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.NOTIFICATION_LIST;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.NOTIFICATION_READ;

@Tag(name = "SSE", description = "SSE 관련 API")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Tag(name = "SSE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "SSE 연결 성공"),
            @ApiResponse(responseCode = "5000", description = "SSE 연결 실패")
    })
    @Operation(summary = "SSE 연결")
    @GetMapping(value="/api/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestHeader(value="Last-Event-ID", required = false, defaultValue = "") String lastEventId ){

        return notificationService.subscribe(userDetails.getMember().getId(), lastEventId);
    }

    @Tag(name = "SSE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "전체 알림 조회 성공")
    })
    @Operation(summary = "전체 알림 조회")
    @GetMapping(value = "/api/notifications")
    public ResponseEntity<SuccessResponse<List<ResponseNotificationDto>>> getNotificationList(@AuthenticationPrincipal CustomUserDetails userDetails){

        return SuccessResponse.toResponseEntity(NOTIFICATION_LIST, notificationService.getNotificationList(userDetails.getMember()));
    }

    @Tag(name = "SSE")
    @Operation(summary = "알림 읽음")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "알림 읽기 성공"),
            @ApiResponse(responseCode = "4049", description = "존재하지 않는 알림")
    })
    @PostMapping("/api/notification/{notificationid}")
    public ResponseEntity<SuccessResponse<Object>> readNotification(@PathVariable Long notificationid, @AuthenticationPrincipal CustomUserDetails userDetails){
        notificationService.readNotification(notificationid,userDetails.getMember());

        return SuccessResponse.toResponseEntity(NOTIFICATION_READ, null);
    }
}