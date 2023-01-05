package com.bluehair.hanghaefinalproject.collaboRequest.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaboRequestRepository extends JpaRepository<CollaboRequest, Long> {

    List<CollaboRequest> findAllByPostId(Long id);
}
