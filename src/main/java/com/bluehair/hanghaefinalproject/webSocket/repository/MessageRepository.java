package com.bluehair.hanghaefinalproject.webSocket.repository;

import com.bluehair.hanghaefinalproject.webSocket.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoom_RoomIdOrderByCreatedAtDesc(Long roomId);

}

