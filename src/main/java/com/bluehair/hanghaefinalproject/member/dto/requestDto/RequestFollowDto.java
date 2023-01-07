package com.bluehair.hanghaefinalproject.member.dto.requestDto;

import com.bluehair.hanghaefinalproject.member.dto.serviceDto.FollowDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Follow 요청 DTO")
@Getter
public class RequestFollowDto {
    @Schema(description = "팔로우 할 회원 닉네임", required = true, example = "test01")
    private String myFollowingMemberNickname;

    @Schema(description = "Follow 되어있는지 여부", required = true, example = "false")
    private Boolean isFollowed;

    public FollowDto toFollowDto() {
        return FollowDto.builder()
                .myFollowingMemberNickname(myFollowingMemberNickname)
                .build();
    }
}
