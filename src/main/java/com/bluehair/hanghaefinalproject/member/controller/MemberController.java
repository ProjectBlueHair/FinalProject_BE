package com.bluehair.hanghaefinalproject.member.controller;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.member.dto.RequestLoginMemberDto;
import com.bluehair.hanghaefinalproject.member.dto.RequestSignUpMemberDto;
import com.bluehair.hanghaefinalproject.member.service.MemberService;

import io.swagger.annotations.ApiOperation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.LOGIN_MEMBER;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.SIGNUP_MEMBER;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.TOKEN_REISSUANCE;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return SuccessResponse.toResponseEntity(SIGNUP_MEMBER, null);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "4091", description = "이메일 중복", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "회원 가입", description = "신규 회원 등록")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RequestSignUpMemberDto requestSignUpMemberDto) {
        memberService.signUp(requestSignUpMemberDto.toSignUpMemberDto());
        return SuccessResponse.toResponseEntity(SIGNUP_MEMBER, null);
    }

    @ApiOperation(value = "로그인", notes = "회원 로그인", response = SuccessResponse.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginMemberDto requestLoginMemberDto, HttpServletResponse response) {
        memberService.login(requestLoginMemberDto.toLoginMemberDto(), response);
        return SuccessResponse.toResponseEntity(LOGIN_MEMBER, null);
    }

    @ApiOperation(value = "토큰 재발행", notes = "토큰 재발행", response = SuccessResponse.class)
    @PostMapping("/reissuance")
    public ResponseEntity<?> reissuance(HttpServletRequest request, HttpServletResponse response){
        memberService.tokenReissuance(request, response);
        return SuccessResponse.toResponseEntity(TOKEN_REISSUANCE, null);
    }

}
