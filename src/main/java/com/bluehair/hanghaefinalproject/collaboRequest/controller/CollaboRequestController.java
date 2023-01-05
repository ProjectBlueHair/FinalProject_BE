package com.bluehair.hanghaefinalproject.collaboRequest.controller;

import com.bluehair.hanghaefinalproject.collaboRequest.dto.RequestCollaboRequestDto;
import com.bluehair.hanghaefinalproject.collaboRequest.service.CollaboRequestService;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.COLLABO_REQUEST;

import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
public class CollaboRequestController {

    private final CollaboRequestService collaboRequestService;

    @ApiOperation(value = "작성", notes = "콜라보리퀘스트 작성", response = SuccessResponse.class)
    @PostMapping("/api/post/{postid}/collabo")
    public ResponseEntity<?> collaboRequest(@PathVariable Long postid, @RequestBody RequestCollaboRequestDto requestCollaboRequestDto, @ApiIgnore @AuthenticationPrincipal CustomUserDetails customUserDetails){
        collaboRequestService.collaboRequest(postid, requestCollaboRequestDto.tocollaboRequestDetailsDto(), requestCollaboRequestDto.tosaveMusicDto(), customUserDetails.getMember());

        return SuccessResponse.toResponseEntity(COLLABO_REQUEST, null);
    }

    @ApiOperation(value = "조회", notes = "콜라보리퀘스트 상세 조회", response = SuccessResponse.class)
    @GetMapping("/api/collabo/{collaborequestid}")
    public ResponseEntity<?> getCollaboRequest(@PathVariable Long collaborequestid){
        

        return SuccessResponse.toResponseEntity(COLLABO_REQUEST,collaboRequestService.getCollaboRequest(collaborequestid));
    }


}
