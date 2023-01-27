package com.bluehair.hanghaefinalproject.member.repository.JPA;

import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import com.bluehair.hanghaefinalproject.member.repository.MemberDetailRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAMemberDetailRepository extends JpaRepository<MemberDetail, Long>, MemberDetailRepository {

}
