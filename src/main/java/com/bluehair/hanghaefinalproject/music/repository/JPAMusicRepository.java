package com.bluehair.hanghaefinalproject.music.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAMusicRepository extends JpaRepository<Music, Long> {
    Music findByCollaboRequest_Id(Long id);
    List<Music> findAllByCollaboRequest(CollaboRequest collaboRequest);

    List<Music> findAllByCollaboRequestId(long collaboid);

    List<Music> findAllByCollaboRequest_Nickname(String nickname);
}
