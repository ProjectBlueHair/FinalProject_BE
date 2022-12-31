package com.bluehair.hanghaefinalproject.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestDto {
    private Long id;
    private String password;

    @Builder
    public TestDto(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
