package com.bluehair.hanghaefinalproject.comment.repository;

import com.bluehair.hanghaefinalproject.comment.entity.Comment;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long postId);

    void deleteById(Long commentId);

    List<Comment> findByParentsId(Long parentsId);

    List<Comment> findByPostId(Long postId);

    List<Comment> findByPostIdAndParentsId(Long postId, Long parentsId);

    @Transactional
    @Modifying
    @Query("DELETE from Comment c where c.post = :Post")
    void deleteAllByPost(@Param("Post") Post post);

    void updateNickname(String before, String after);
}
