package com.bluehair.hanghaefinalproject.member.mapper;

import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMemberInfoDto;
import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SignUpDto;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapStruct {
    MemberMapStruct MEMBER_MAPPER = Mappers.getMapper(MemberMapStruct.class);
    Member SignUpDtoToMember(SignUpDto signUpMemberDto);

    default ResponseMemberInfoDto userDetailsToResponseMemberInfoDto(CustomUserDetails userDetails){
        return ResponseMemberInfoDto.builder()
                .email(userDetails.getMember().getEmail())
                .nickname(userDetails.getMember().getNickname())
                .profileImg(userDetails.getMember().getProfileImg())
                .build();
    }
}
