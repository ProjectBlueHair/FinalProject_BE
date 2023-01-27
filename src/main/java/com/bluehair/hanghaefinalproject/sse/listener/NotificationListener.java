package com.bluehair.hanghaefinalproject.sse.listener;

import com.bluehair.hanghaefinalproject.sse.dto.RequestNotificationDto;
import com.bluehair.hanghaefinalproject.sse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void handleNotification(RequestNotificationDto requestNotificationDto){
        notificationService.send(requestNotificationDto.getReceiver(), requestNotificationDto.getSender(),requestNotificationDto.getNotificationType(),
                requestNotificationDto.getContent(), requestNotificationDto.getType(), requestNotificationDto.getTypeId(), requestNotificationDto.getPostId());
        log.info("EventListener has been operated. Sender Id: " + requestNotificationDto.getSender().getId() + "NotificationType: " +requestNotificationDto.getNotificationType());
    }

}
