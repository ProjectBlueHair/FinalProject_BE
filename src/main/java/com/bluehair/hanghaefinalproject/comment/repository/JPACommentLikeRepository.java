package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPACommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepository{

}
