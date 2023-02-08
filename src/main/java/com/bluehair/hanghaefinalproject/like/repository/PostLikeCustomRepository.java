package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.PostLike;
import com.bluehair.hanghaefinalproject.like.entity.PostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface PostLikeCustomRepository {
    @Transactional
    PostLike save(PostLike postLike);
    @Transactional
    void deleteById(PostLikeCompositeKey postLikeCompositeKey);

    @Transactional
    Optional<PostLike> findById(PostLikeCompositeKey postLikeCompositeKey);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="3000")})
    Optional<PostLike> findByPostLikedIdAndMemberId(Long postLikedId, Long memberId);

    @Transactional
    @Modifying
    @Query("DELETE from PostLike c where c.postLiked = :Post")
    void deleteAllByPost(@Param("Post") Post post);

}
