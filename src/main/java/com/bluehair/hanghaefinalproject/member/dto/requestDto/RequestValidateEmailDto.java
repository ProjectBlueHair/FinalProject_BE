package com.bluehair.hanghaefinalproject.member.dto.requestDto;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.ValidateEmailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "이메일 중복 확인 요청 Dto")
@Getter
public class RequestValidateEmailDto {
    @Schema(description = "이메일", required = true, example = "test01@test.com")
    private String email;

    public ValidateEmailDto toValidateEmailDto() {
        return ValidateEmailDto.builder()
                .email(email)
                .build();
    }
}
