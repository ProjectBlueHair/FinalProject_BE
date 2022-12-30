package com.bluehair.hanghaefinalproject.member.service;

import com.bluehair.hanghaefinalproject.common.exception.ErrorCode;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.exception.InvalidSignUpRequestException;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void test(){
        Member member = memberRepository.findByEmail("asdfasdfasdf")
                .orElseThrow(()-> new InvalidSignUpRequestException(ErrorCode.INVALID_EMAIL));
    }
}
