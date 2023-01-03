package com.bluehair.hanghaefinalproject.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole role = MemberRole.SILVER;
    @Column
    private String refreshToken;

    @Builder
    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
