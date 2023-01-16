package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAPostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepository {
}
