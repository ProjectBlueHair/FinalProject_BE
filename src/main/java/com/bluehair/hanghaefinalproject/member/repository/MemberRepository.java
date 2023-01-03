package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByEmail(String email);

}