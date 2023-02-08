package com.bluehair.hanghaefinalproject.webSocket.repository;

import org.springframework.data.repository.query.Param;

public interface MessageCustomRepository {
    void updateNickname(@Param("before") String before, @Param("after") String after);
}
