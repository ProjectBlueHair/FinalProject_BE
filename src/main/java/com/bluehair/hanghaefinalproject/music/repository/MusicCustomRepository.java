package com.bluehair.hanghaefinalproject.music.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import org.springframework.data.repository.query.Param;

public interface MusicCustomRepository {
    void deleteAllByCollaboRequest(@Param("collaboRequest") CollaboRequest collaboRequestId);
}
