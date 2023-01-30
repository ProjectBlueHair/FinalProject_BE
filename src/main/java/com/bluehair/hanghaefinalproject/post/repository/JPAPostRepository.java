package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JPAPostRepository extends JpaRepository<Post, Long>, PostRepository {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p set p.nickname = :after where p.nickname = :nickname")
    void updateNickname(@Param("before") String before, @Param("after") String after);
}
