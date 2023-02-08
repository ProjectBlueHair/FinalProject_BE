package com.bluehair.hanghaefinalproject.tag.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import static com.bluehair.hanghaefinalproject.tag.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public void deleteAllByPost(@Param("Post") Post post){
        jpaQueryFactory.delete(tag)
                .where(tag.post.eq(post))
                .execute();

    }
}
