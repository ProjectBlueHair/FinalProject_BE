package com.bluehair.hanghaefinalproject.common.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Domain이 커지게 될 경우, 해당 객체를 Abstract로 선언 후 Domain별 extend
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member
    INVALID_EMAIL(HttpStatus.OK, "유효하지 않은 이메일입니다.", 4000),
    INVALID_PASSWORD(HttpStatus.OK, "유효하지 않은 비밀번호입니다.", 4000),
    PASSWORD_INCORRECT(HttpStatus.OK, "계정 정보가 일치하지 않습니다.", 4000),
    DUPLICATED_EMAIL(HttpStatus.OK, "중복된 이메일입니다.", 4090),
    DUPLICATED_NICKNAME(HttpStatus.OK, "중복된 닉네임입니다.", 4090),
    MEMBER_NOT_FOUND(HttpStatus.OK, "존재하지 않는 회원입니다.", 4040),
    ALREADY_FOLLWED(HttpStatus.OK, "이미 팔로우한 회원입니다.", 4040),
    ALREADY_UNFOLLWED(HttpStatus.OK, "이미 언팔로우한 회원입니다.", 4040),

    // JWT
    ACCESSTOKEN_NOT_EXIST(HttpStatus.OK, "Access Token이 존재하지 않습니다.", 4011),
    REFRESHTOKEN_NOT_EXIST(HttpStatus.OK, "Refresh Token이 존재하지 않습니다.", 4012),
    INVALID_ACCESSTOKEN(HttpStatus.OK, "유효하지 않은 Access Token입니다.", 4013),
    INVALID_REFRESHTOKEN(HttpStatus.OK, "유효하지 않은 Refresh Token입니다.", 4014),
    EXPIRED_ACCESSTOKEN(HttpStatus.OK, "만료된 Access Token입니다.", 4015),
    EXPIRED_REFRESHTOKEN(HttpStatus.OK, "만료된 Refresh Token입니다.", 4016),
    MEMBER_NOT_AUTHORIZED(HttpStatus.OK, "접근 권한이 없는 사용자입니다.", 4031),


    // Post
    POST_NOT_FOUND(HttpStatus.OK, "존재하지 않는 게시글입니다.", 4041),
    ALREADY_ARCHIVED(HttpStatus.OK, "이미 보관된 게시글입니다.", 4001),
    ALREADY_CANCELED(HttpStatus.OK, "이미 보관 취소된 게시글입니다.", 4001),
    // Comment
    COMMENT_NOT_FOUND(HttpStatus.OK, "존재하지 않는 댓글입니다.", 4044),

    COLLABO_NOT_FOUND(HttpStatus.OK, "콜라보리퀘스트가 존재하지 않습니다.", 4042),
    COLLABO_ALREADY_APPROVED(HttpStatus.OK, "승인된 콜라보 리퀘스트는 수정 또는 삭제할 수 없습니다.", 4002),
    INVALID_SOUNDSAMPLE(HttpStatus.OK, "유효하지 않은 음원입니다.", 4003),
    NOTIFICATION_NOT_FOUND(HttpStatus.OK, "알림이 존재하지 않습니다", 4049),

    ALREADY_LIKEDPOST(HttpStatus.OK, "이미 좋아요한 게시물입니다.", 4048),

    // Server Error
    UNHANDLED_SERVER_ERROR(HttpStatus.OK, "처리되지 않은 서버 에러입니다.", 5000);

    private final HttpStatus httpStatus;
    private final String message;
    private final Integer customHttpStatusCode;

}
