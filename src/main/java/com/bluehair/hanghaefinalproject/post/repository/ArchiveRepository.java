package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.member.entity.Member;
import com.bluehair.hanghaefinalproject.post.entity.Archive;
import com.bluehair.hanghaefinalproject.post.entity.ArchiveCompositeKey;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArchiveRepository {
    Archive save(Archive follow);
    void deleteById(ArchiveCompositeKey archiveCompositeKey);
    Boolean existsById(ArchiveCompositeKey archiveCompositeKey);
    List<Archive> findAllByMember(Pageable pageable, Member member);
}
