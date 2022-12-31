package com.bluehair.hanghaefinalproject.member.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.member.dto.RequestLoginMemberDto;
import com.bluehair.hanghaefinalproject.member.dto.RequestSignUpMemberDto;
import com.bluehair.hanghaefinalproject.member.dto.TestDto;
import com.bluehair.hanghaefinalproject.member.service.MemberService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.LOGIN_MEMBER;
import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.SIGNUP_MEMBER;

@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @ApiOperation(value = "테스트", notes = "테스트용입니다.")
    @GetMapping("/test")
    public ResponseEntity<?> test(){
        memberService.test();
        return SuccessResponse.toResponseEntity(SIGNUP_MEMBER, null);
    }
    @ApiOperation(value = "회원 가입", notes = "신규 회원 등록")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RequestSignUpMemberDto requestSignUpMemberDto) {
        memberService.signUp(requestSignUpMemberDto.toSignUpMemberDto());
        return SuccessResponse.toResponseEntity(SIGNUP_MEMBER, null);
    }

    @ApiOperation(value = "회원 가입", notes = "신규 회원 등록")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginMemberDto requestLoginMemberDto) {
        TestDto data = memberService.login(requestLoginMemberDto.toLoginMemberDto());
        return SuccessResponse.toResponseEntity(LOGIN_MEMBER, data);
    }
}
