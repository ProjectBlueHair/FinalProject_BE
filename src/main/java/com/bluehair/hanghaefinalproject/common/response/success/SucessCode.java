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
    FOLLOW_MEMBER(HttpStatus.OK, "팔로우 성공", 2000),
    UNFOLLOW_MEMBER(HttpStatus.OK, "언팔로우 성공", 2000),
    MEMBER_INFO(HttpStatus.OK, "사용자 정보 반환 성공", 2000),
    TOKEN_REISSUANCE(HttpStatus.OK, "토큰 재발행 성공", 2001),

    // Post
    INFO_POST(HttpStatus.OK, "상세 게시글 조회 성공", 2000),
    CREATE_POST(HttpStatus.OK, "게시글 작성 성공", 2000),
    MAIN_POST(HttpStatus.OK, "게시글 전체 조회 성공",2000),

    //Comment
    CREATE_COMMENT(HttpStatus.OK,"댓글 작성 성공",2000),
    UPDATE_COMMENT(HttpStatus.OK, "댓글 수정 성공", 2000),
    DELETE_COMMENT(HttpStatus.OK, "댓글 삭제 성공", 2000),

    //CollaboRequest
    COLLABO_REQUEST_SUCCESS(HttpStatus.OK, "콜라보 리퀘스트 성공", 2000),
    COLLABO_REQUEST(HttpStatus.OK, "콜라보 리퀘스트 상세 조회 성공", 2000),
    COLLABO_LIST(HttpStatus.OK, "게시글 콜라보 리퀘스트 목록 조회 성공", 2000),

    COLLABO_REQUEST_APPROVAL(HttpStatus.OK, "콜라보 리퀘스트 승인 성공", 2000),
    COLLABO_REQUEST_DELETE(HttpStatus.OK, "콜라보 리퀘스트 삭제 성공", 2000),

    COLLABO_REQUEST_UPDATE(HttpStatus.OK, "콜라보 리퀘스트 수정 성공", 2000);

    private final HttpStatus httpStatus;
    private final String message;
    private final Integer customHttpStatusCode;
}
