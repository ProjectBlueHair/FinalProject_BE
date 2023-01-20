package com.bluehair.hanghaefinalproject.sse.entity;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
public class Sender {
    private String sender;
    private String senderImg;

    public Sender(Member sender) {
        this.sender = sender.getNickname();
        this.senderImg = sender.getProfileImg();
    }
}
