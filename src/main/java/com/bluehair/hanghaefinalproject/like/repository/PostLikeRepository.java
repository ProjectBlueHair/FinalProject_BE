package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.PostLike;
import com.bluehair.hanghaefinalproject.like.entity.PostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public interface PostLikeRepository {
    PostLike save(PostLike postLike);
    @Transactional
    void deleteById(PostLikeCompositeKey postLikeCompositeKey);
    Optional<PostLike> findById(PostLikeCompositeKey postLikeCompositeKey);
    @Transactional
    @Modifying
    @Query("DELETE from PostLike c where c.postLiked = :Post")
    void deleteAllByPost(@Param("Post") Post post);
}
