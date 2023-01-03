package com.bluehair.hanghaefinalproject.security.service;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.member.repository.MemberRepository;
import com.bluehair.hanghaefinalproject.security.CustomUserDetails;
import com.bluehair.hanghaefinalproject.security.exception.InvalidMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.bluehair.hanghaefinalproject.common.response.error.ErrorCode.MEMBER_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String Email) throws InvalidMemberException {
        Member member = memberRepository.findByEmail(Email)
                .orElseThrow(()->new InvalidMemberException(MEMBER_NOT_EXIST));
        return new CustomUserDetails(member, member.getPassword());
    }
}
