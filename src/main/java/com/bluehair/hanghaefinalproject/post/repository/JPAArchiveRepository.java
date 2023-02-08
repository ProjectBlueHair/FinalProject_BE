package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.post.entity.Archive;
import com.bluehair.hanghaefinalproject.post.entity.ArchiveCompositeKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAArchiveRepository extends JpaRepository<Archive, Long>{
    Archive save(Archive follow);
    void deleteById(ArchiveCompositeKey archiveCompositeKey);
    Boolean existsById(ArchiveCompositeKey archiveCompositeKey);
    List<Archive> findAllByMember(Pageable pageable, Member member);
}
