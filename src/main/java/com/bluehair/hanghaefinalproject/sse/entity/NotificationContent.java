package com.bluehair.hanghaefinalproject.sse.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class NotificationContent {
    @Column(nullable = false)
    private String content;

    public NotificationContent(String content){
        this.content = content;
    }
}
