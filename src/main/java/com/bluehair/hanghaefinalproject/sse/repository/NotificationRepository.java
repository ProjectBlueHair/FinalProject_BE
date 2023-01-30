package com.bluehair.hanghaefinalproject.sse.repository;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.sse.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiverOrderByCreatedAtDesc(Member member);

    @Transactional
    @Query("SELECT COUNT(n) from Notification n where n.receiver.nickname =:nickname and n.isRead = false")
    Long countUnreadNotifications(String nickname);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notification n set n.sender.sender = :after where n.sender.sender = :before")
    void updateNickname(@Param("before") String before, @Param("after") String after);
}
