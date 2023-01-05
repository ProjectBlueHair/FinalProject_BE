package com.bluehair.hanghaefinalproject.collaboRequest.controller;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.RequestCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.service.CollaboRequestService;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.COLLABO_REQUEST;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
public class CollaboRequestController {

    private final CollaboRequestService collaboRequestService;

    @Operation(summary = "콜라보 리퀘스트 요청", description = "콜라보 리퀘스트 요청 성공")
    @PostMapping("/api/post/{postid}/collabo")
    public ResponseEntity<?> collaboRequest(@PathVariable Long postid, @RequestBody RequestCollaboRequestDto requestCollaboRequestDto, @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails){
        collaboRequestService.collaboRequest(postid, requestCollaboRequestDto.tocollaboRequestDetailsDto(), requestCollaboRequestDto.tosaveMusicDto(), customUserDetails.getMember());

        return SuccessResponse.toResponseEntity(COLLABO_REQUEST, null);

    }
}
