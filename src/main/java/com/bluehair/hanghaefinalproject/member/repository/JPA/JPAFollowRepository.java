package com.bluehair.hanghaefinalproject.member.repository.JPA;

import com.bluehair.hanghaefinalproject.member.entity.Follow;
import com.bluehair.hanghaefinalproject.member.entity.FollowCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAFollowRepository extends JpaRepository<Follow, Long> {
    Follow save(Follow follow);
    void deleteById(FollowCompositeKey followCompositeKey);
    Boolean existsById(FollowCompositeKey followCompositeKey);
}
