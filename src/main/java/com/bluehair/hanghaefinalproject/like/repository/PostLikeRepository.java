package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.PostLike;
import com.bluehair.hanghaefinalproject.like.entity.PostLikeCompositeKey;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

@Component
public interface PostLikeRepository {
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
}
