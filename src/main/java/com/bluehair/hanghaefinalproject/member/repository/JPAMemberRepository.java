package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JPAMemberRepository extends JpaRepository<Member, Long>, MemberRepository  {
    @Modifying
    @Query("UPDATE Member SET followingCount = ?1 WHERE id = ?2")
    void updateFollowingCount(Long followingCount, Long memberId);

    @Modifying
    @Query("UPDATE Member SET followerCount =?1 WHERE id = ?2")
    void updateFollowerCount(Long followerCount, Long memberId);
}
