package com.bluehair.hanghaefinalproject.member.entity;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;


@RedisHash(value = "RefreshTokens", timeToLive = 1800)
@Getter
public class RefreshToken {
    @Id
    private String id;
    private String token;

    public RefreshToken(String id, String token) {
        this.id = id;
        this.token = token;
    }
}
