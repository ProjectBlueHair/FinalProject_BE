package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    void updateFollowingCount(Long followingCount, Long memberId);
    void updateFollowerCount(Long followerCount, Long memberId);

}
