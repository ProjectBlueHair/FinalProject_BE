package com.bluehair.hanghaefinalproject.member.mapper;

import com.bluehair.hanghaefinalproject.member.dto.SignUpMemberDto;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapStruct {
    MemberMapStruct MEMBER_MAPPER = Mappers.getMapper(MemberMapStruct.class);
    Member SignUpMemberDtoToMember(SignUpMemberDto signUpMemberDto);
}
