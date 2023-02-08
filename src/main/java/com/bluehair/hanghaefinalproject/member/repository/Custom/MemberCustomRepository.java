package com.bluehair.hanghaefinalproject.member.repository.Custom;

public interface MemberCustomRepository {
    void updateFollowerCount(Long followerCount, Long memberId);
    void updateFollowingCount(Long followingCount, Long memberId);
}
