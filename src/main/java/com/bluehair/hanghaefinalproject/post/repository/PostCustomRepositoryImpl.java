package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.bluehair.hanghaefinalproject.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Post post1 = jpaQueryFactory.select(post)
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
        return Optional.ofNullable(post1);
    }

    @Override
    public List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable) {
        return jpaQueryFactory.selectFrom(post)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Post> findByTitleContainsOrContentsContains(Pageable pageable, String search, String searchContents) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.title.contains(search).or(post.title.contains(searchContents)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Post> findByContentsContains(Pageable pageable, String search) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.contents.contains(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Post> findByNickname(Pageable pageable, String nickname) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.nickname.eq(nickname))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public void deleteById(Long postId) {
        jpaQueryFactory.delete(post)
                .where(post.id.eq(postId))
                .execute();
    }

    @Override
    public void updateNickname(String before, String after) {
        jpaQueryFactory
                .update(post)
                .where(post.nickname.eq(before))
                .set(post.nickname, after)
                .execute();

    }
}
