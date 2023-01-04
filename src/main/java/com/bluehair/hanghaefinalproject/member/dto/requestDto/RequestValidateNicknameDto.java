package com.bluehair.hanghaefinalproject.member.dto.requestDto;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.ValidateNicknameDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "닉네임 중복 확인 요청 Dto")
@Getter
public class RequestValidateNicknameDto {
    @Schema(description = "닉네임", required = true, example = "test01")
    private String nickname;

    public ValidateNicknameDto toValidateNicknameDto() {
        return ValidateNicknameDto.builder()
                .nickname(nickname)
                .build();
    }
}
