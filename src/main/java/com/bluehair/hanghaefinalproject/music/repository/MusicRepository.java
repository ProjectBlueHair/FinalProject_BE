package com.bluehair.hanghaefinalproject.music.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import com.bluehair.hanghaefinalproject.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Music findByCollaboRequest_Id(Long id);
    List<Music> findAllByCollaboRequest(CollaboRequest collaboRequest);

    List<Music> findAllByCollaboRequestId(long collaboid);
    @Transactional
    @Modifying
    @Query("DELETE from Music c where c.collaboRequest = :collaboRequest")
    void deleteAllByCollaboRequest(@Param("collaboRequest") CollaboRequest collaboRequestId);
}
