package com.bluehair.hanghaefinalproject.member.service;

import com.bluehair.hanghaefinalproject.member.dto.LoginMemberDto;
import com.bluehair.hanghaefinalproject.member.dto.SignUpMemberDto;
import com.bluehair.hanghaefinalproject.member.dto.TestDto;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.exception.InvalidSignUpRequestException;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.*;
import static com.bluehair.hanghaefinalproject.member.mapper.MemberMapStruct.MEMBER_MAPPER;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void test(){
        Member member = memberRepository.findByEmail("asdfasdfasdf")
                .orElseThrow(()-> new InvalidSignUpRequestException(INVALID_EMAIL));
    }

    @Transactional
    public void signUp(SignUpMemberDto signUpMemberDto) {
        memberRepository.findByEmail(signUpMemberDto.getEmail())
                .ifPresent(m->{
                    throw new InvalidSignUpRequestException(DUPLICATED_EMAIL);
                });
        Member member = MEMBER_MAPPER.SignUpMemberDtoToMember(signUpMemberDto);

        System.out.println("Member: " + member.getEmail());
        memberRepository.save(member);
    }

    @Transactional
    public TestDto login(LoginMemberDto loginMemberDto) {
        Member member = memberRepository.findByEmail(loginMemberDto.getEmail())
                .orElseThrow(()->new InvalidSignUpRequestException(MEMBER_NOT_EXIST));
        return MEMBER_MAPPER.MemberToTestDto(member);
    }
}
