package com.bluehair.hanghaefinalproject.sse.dto;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.sse.entity.NotificationType;
import com.bluehair.hanghaefinalproject.sse.entity.RedirectionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RequestNotificationDto {
    private Member receiver;
    private Member sender;
    NotificationType notificationType;
    private String content;
    private RedirectionType type;
    private Long typeId;
    private Long postId;

    public RequestNotificationDto(Member receiver, Member sender, NotificationType notificationType,
                                  String content, RedirectionType type, Long typeId, Long postId) {
        this.receiver = receiver;
        this.sender = sender;
        this.notificationType = notificationType;
        this.content = content;
        this.type = type;
        this.typeId = typeId;
        this.postId = postId;
    }
}
