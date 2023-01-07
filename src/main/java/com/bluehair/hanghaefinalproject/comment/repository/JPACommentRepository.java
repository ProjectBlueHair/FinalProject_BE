package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPACommentRepository extends JpaRepository<Comment, Long>, CommentRepository {
}
