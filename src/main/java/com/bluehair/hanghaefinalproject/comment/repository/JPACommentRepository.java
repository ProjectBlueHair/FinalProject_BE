package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface JPACommentRepository extends JpaRepository<Comment, Long>, CommentRepository {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Comment c set c.nickname = :after where c.nickname = :before")
    void updateNickname(@Param("before") String before, @Param("after") String after);
}
