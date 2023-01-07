package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAFollowRepository extends JpaRepository<Follow, Long>, FollowRepository {

}
