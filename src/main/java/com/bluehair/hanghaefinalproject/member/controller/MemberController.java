package com.bluehair.hanghaefinalproject.member.controller;

import com.bluehair.hanghaefinalproject.common.response.success.SuccessResponse;
import com.bluehair.hanghaefinalproject.member.dto.requestDto.RequestLoginDto;
import com.bluehair.hanghaefinalproject.member.dto.requestDto.RequestSignUpDto;
import com.bluehair.hanghaefinalproject.member.dto.requestDto.RequestValidateEmailDto;
import com.bluehair.hanghaefinalproject.member.dto.requestDto.RequestValidateNicknameDto;
import com.bluehair.hanghaefinalproject.member.service.MemberService;

import io.swagger.annotations.ApiOperation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bluehair.hanghaefinalproject.common.response.success.SucessCode.*;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "사용 가능한 이메일"),
            @ApiResponse(responseCode = "4091", description = "이메일 중복"),
            @ApiResponse(responseCode = "4001", description = "유효하지 않은 이메일")
    })
    @Operation(summary = "이메일 검증", description = "이메일 중복 확인, 이메일 형식 확인")
    @PostMapping("/validate/email")
    public ResponseEntity<?> validateEmail(@RequestBody RequestValidateEmailDto requestValidateEmailDto) {
        memberService.validateEmail(requestValidateEmailDto.toValidateEmailDto());
        return SuccessResponse.toResponseEntity(VALID_EMAIL, null);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "사용 가능한 닉네임"),
            @ApiResponse(responseCode = "4092", description = "닉네임 중복"),
    })
    @Operation(summary = "닉네임 검증", description = "닉네임 중복 확인")
    @PostMapping("validate/nickname")
    public ResponseEntity<?> validateNickname(@RequestBody RequestValidateNicknameDto requestValidateNicknameDto) {
        memberService.validateNickname(requestValidateNicknameDto.toValidateNicknameDto());
        return SuccessResponse.toResponseEntity(VALID_NICKNAME, null);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "2000", description = "회원 가입 성공"),
            @ApiResponse(responseCode = "4002", description = "유효하지 않은 비밀번호"),
            @ApiResponse(responseCode = "4091", description = "이메일 중복"),
            @ApiResponse(responseCode = "4092", description = "닉네임 중복")
    })
    @Operation(summary = "회원 가입", description = "이메일 및 닉네임 중복 확인, Password Encrypt, DB 저장")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RequestSignUpDto requestSignUpDto) {
        memberService.signUp(requestSignUpDto.toSignUpDto());
        return SuccessResponse.toResponseEntity(SIGNUP_MEMBER, null);
    }

    @ApiOperation(value = "로그인", notes = "회원 로그인", response = SuccessResponse.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletResponse response) {
        memberService.login(requestLoginDto.toLoginMemberDto(), response);
        return SuccessResponse.toResponseEntity(LOGIN_MEMBER, null);
    }

    @ApiOperation(value = "토큰 재발행", notes = "토큰 재발행", response = SuccessResponse.class)
    @PostMapping("/reissuance")
    public ResponseEntity<?> reissuance(HttpServletRequest request, HttpServletResponse response){
        memberService.tokenReissuance(request, response);
        return SuccessResponse.toResponseEntity(TOKEN_REISSUANCE, null);
    }

}
