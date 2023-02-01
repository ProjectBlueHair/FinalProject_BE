package com.bluehair.hanghaefinalproject.webSocket.entity;

import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ChatMessage extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String nickname;
    @Column
    private String profileImg;
    @Column
    private String message;
    @Column
    private String date;
    @Column
    private String time;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(String nickname, String profileImg, String message,String date , String time, ChatRoom chatRoom){
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.message = message;
        this.date = date;
        this.time = time;
        this.chatRoom = chatRoom;
    }
}
