package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPACommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepository{

}
