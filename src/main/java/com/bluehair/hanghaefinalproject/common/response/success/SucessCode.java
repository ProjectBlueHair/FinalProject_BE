package com.bluehair.hanghaefinalproject.common.response.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Domain이 커지게 될 경우, 해당 객체를 Abstract로 선언 후 Domain별 extend
@Getter
@AllArgsConstructor
public enum SucessCode {
    // Member
    VALID_EMAIL(HttpStatus.OK, "사용 가능한 이메일", 2000),
    VALID_NICKNAME(HttpStatus.OK, "사용 가능한 닉네임", 2000),
    SIGNUP_MEMBER(HttpStatus.OK, "회원 가입 성공", 2000),
    LOGIN_MEMBER(HttpStatus.OK, "로그인 성공", 2000),
    TOKEN_REISSUANCE(HttpStatus.OK, "토큰 재발행 성공", 2001);

    private final HttpStatus httpStatus;
    private final String message;
    private final Integer customHttpStatusCode;
}
