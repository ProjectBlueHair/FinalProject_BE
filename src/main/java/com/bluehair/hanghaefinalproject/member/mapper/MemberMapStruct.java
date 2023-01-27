package com.bluehair.hanghaefinalproject.member.mapper;

import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMemberInfoDto;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseMypageDto;
import com.bluehair.hanghaefinalproject.member.dto.responseDto.ResponseSettingDto;
import com.bluehair.hanghaefinalproject.member.dto.serviceDto.SignUpDto;
import com.bluehair.hanghaefinalproject.member.entity.Job;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

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

    default List<Job> SettingMemberDetailDtoToJob(List<String> nameList, MemberDetail memberDetail) {
        List<Job> result = new ArrayList<>();
        for (String s : nameList) {
            Job tmp = Job.builder()
                    .memberDetail(memberDetail)
                    .name(s)
                    .build();
            result.add(tmp);
        }
        return result;
    }
    ResponseSettingDto memberAndMemberDetailToResponseSettingDto(Member member, MemberDetail memberDetail);
    ResponseMypageDto memberAndMemberDetailToResponseMypageDto(Member member, MemberDetail memberDetail, Boolean isMine);
}
