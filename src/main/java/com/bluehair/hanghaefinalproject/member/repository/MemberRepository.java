package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.repository.Custom.MemberCustomRepository;
import com.bluehair.hanghaefinalproject.member.repository.JPA.JPAMemberRepository;
import org.springframework.stereotype.Component;

@Component
public interface MemberRepository extends JPAMemberRepository, MemberCustomRepository {

}
