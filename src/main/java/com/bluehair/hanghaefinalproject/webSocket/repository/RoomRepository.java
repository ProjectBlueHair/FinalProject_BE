package com.bluehair.hanghaefinalproject.webSocket.repository;

import com.bluehair.hanghaefinalproject.webSocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByMember1_Id(Long memberId);
    List<ChatRoom> findByMember2_Id(Long memberId);


}
