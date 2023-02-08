package com.bluehair.hanghaefinalproject.member.repository.JPA;

import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAMemberDetailRepository extends JpaRepository<MemberDetail, Long> {
    MemberDetail save(MemberDetail memberDetail);
}
