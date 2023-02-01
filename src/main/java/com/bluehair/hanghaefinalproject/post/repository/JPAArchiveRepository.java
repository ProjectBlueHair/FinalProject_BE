package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepository {

}
