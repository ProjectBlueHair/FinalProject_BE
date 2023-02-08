package com.bluehair.hanghaefinalproject.like.repository;

import com.bluehair.hanghaefinalproject.like.entity.PostLike;
import com.bluehair.hanghaefinalproject.like.entity.PostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.like.entity.QPostLikeCompositeKey;
import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.bluehair.hanghaefinalproject.like.entity.QPostLike.postLike;

@Repository
@RequiredArgsConstructor
public class PostLikeCustomRepositoryImpl implements PostLikeCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;


    @Override
    public PostLike save(PostLike postLike) {
        em.persist(postLike);
        return postLike;
    }

    @Override
    public void deleteById(PostLikeCompositeKey postLikeCompositeKey) {
        jpaQueryFactory
                .delete(postLike)
                .where(QPostLikeCompositeKey.postLikeCompositeKey.eq(postLikeCompositeKey))
                .execute();
    }

    @Override
    public Optional<PostLike> findById(PostLikeCompositeKey postLikeCompositeKey) {
        PostLike postLike1 = jpaQueryFactory.select(postLike)
                .from(postLike)
                .where(QPostLikeCompositeKey.postLikeCompositeKey.eq(postLikeCompositeKey))
                .fetchOne();
        return Optional.ofNullable(postLike1);
    }

    @Override
    public Optional<PostLike> findByPostLikedIdAndMemberId(Long postLikedId, Long memberId) {
        PostLike postLike1 = jpaQueryFactory.select(postLike)
                .from(postLike)
                .where(postLike.postLiked.id.eq(postLikedId).and(postLike.member.id.eq(memberId)))
                .fetchOne();
        return Optional.ofNullable(postLike1);
    }

    @Override
    public void deleteAllByPost(Post post) {
        jpaQueryFactory
                .delete(postLike)
                .where(postLike.postLiked.eq(post))
                .execute();

    }
}
