package com.bluehair.hanghaefinalproject.webSocket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bluehair.hanghaefinalproject.webSocket.entity.QChatMessage.chatMessage;

@Repository
@RequiredArgsConstructor
public class MessageCustomRepositoryImpl implements MessageCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public void updateNickname(String before, String after) {
        jpaQueryFactory
                .update(chatMessage)
                .where(chatMessage.nickname.eq(before))
                .set(chatMessage.nickname, after)
                .execute();
    }

}
