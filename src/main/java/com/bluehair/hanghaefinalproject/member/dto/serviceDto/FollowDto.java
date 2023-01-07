package com.bluehair.hanghaefinalproject.member.dto.serviceDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowDto {
    private String myFollowingMemberNickname;

    @Builder
    public FollowDto(String myFollowingMemberNickname) {
        this.myFollowingMemberNickname = myFollowingMemberNickname;
    }
}
