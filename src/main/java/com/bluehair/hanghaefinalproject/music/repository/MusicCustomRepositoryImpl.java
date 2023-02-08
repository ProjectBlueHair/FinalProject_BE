package com.bluehair.hanghaefinalproject.music.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.bluehair.hanghaefinalproject.music.entity.QMusic.music;

@Repository
@RequiredArgsConstructor
public class MusicCustomRepositoryImpl implements MusicCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    @Override
    public void deleteAllByCollaboRequest(CollaboRequest collaboRequestId) {
        jpaQueryFactory
                .delete(music)
                .where(music.collaboRequest.eq(collaboRequestId))
                .execute();
    }
}
