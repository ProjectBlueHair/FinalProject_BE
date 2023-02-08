package com.bluehair.hanghaefinalproject.webSocket.repository;

import com.bluehair.hanghaefinalproject.webSocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<ChatRoom, Long>, RoomCustomRepository {
    List<ChatRoom> findByMember1_IdOrderByModifiedAtDesc(Long memberId);
    List<ChatRoom> findByMember2_IdOrderByModifiedAtDesc(Long memberId);
    Optional<ChatRoom> findByMember1_IdAndMember2_Id(Long member1Id, Long member2Id);

}
