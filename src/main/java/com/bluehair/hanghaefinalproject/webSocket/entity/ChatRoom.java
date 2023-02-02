package com.bluehair.hanghaefinalproject.webSocket.entity;

import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import com.bluehair.hanghaefinalproject.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@Setter
public class ChatRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    private Member member1;

    @ManyToOne
    private Member member2;

    @Column
    private String finalMesaage;

    @Builder
    public ChatRoom (Member member1, Member member2) {
        this.member1 = member1;
        this.member2 = member2;
    }

    public void update(String finalMesaage){
        this.finalMesaage = finalMesaage;
    }

}