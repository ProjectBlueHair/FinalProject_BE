package com.bluehair.hanghaefinalproject.sse.entity;

import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Notification extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NotificationContent content;

    @Embedded
    private RelatedUrl url;

    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member receiver;

    @Embedded
    private Sender sender;

    @Builder
    public Notification(Member receiver,
                        NotificationType notificationType,
                        String content,
                        RedirectionType type,
                        Long typeId,
                        Member sender
                        ) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = new NotificationContent(content);
        this.url = new RelatedUrl(type, typeId);
        this.sender = new Sender(sender);
        this.isRead = false;
    }

    public String getContent() {
        return content.getContent();
    }

    public void read(){
        isRead = true;
    }
}


