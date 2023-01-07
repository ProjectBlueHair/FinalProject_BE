package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.Follow;
import com.bluehair.hanghaefinalproject.member.entity.FollowCompositeKey;
import org.springframework.stereotype.Component;

@Component
public interface FollowRepository {
    Follow save(Follow follow);
    void deleteById(FollowCompositeKey followCompositeKey);
    Boolean existsById(FollowCompositeKey followCompositeKey);
}
