package com.bluehair.hanghaefinalproject.webSocket.repository;

import com.bluehair.hanghaefinalproject.webSocket.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_RoomIdOrderByCreatedAtDesc(Long roomId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE ChatMessage c set c.nickname = :after where c.nickname = :nickname")
    void updateNickname(@Param("before") String before, @Param("after") String after);
}

