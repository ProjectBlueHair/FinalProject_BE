package com.bluehair.hanghaefinalproject.postLike.repository;

import com.bluehair.hanghaefinalproject.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAPostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepository {
}
