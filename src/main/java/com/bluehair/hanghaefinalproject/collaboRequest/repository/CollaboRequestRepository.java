package com.bluehair.hanghaefinalproject.collaboRequest.repository;

import com.bluehair.hanghaefinalproject.collaboRequest.entity.CollaboRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollaboRequestRepository extends JpaRepository<CollaboRequest, Long> {

    Optional<CollaboRequest> findAllByPostId(Long postId);
}
