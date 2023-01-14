package com.bluehair.hanghaefinalproject.postLike.repository;

import com.bluehair.hanghaefinalproject.postLike.entity.PostLike;
import com.bluehair.hanghaefinalproject.postLike.entity.PostLikeCompositeKey;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public interface PostLikeRepository {
    PostLike save(PostLike postLike);
    @Transactional
    void deleteById(PostLikeCompositeKey postLikeCompositeKey);
    Optional<PostLike> findById(PostLikeCompositeKey postLikeCompositeKey);
}
