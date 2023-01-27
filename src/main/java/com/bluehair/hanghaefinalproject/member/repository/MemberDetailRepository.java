package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import org.springframework.stereotype.Component;

@Component
public interface MemberDetailRepository {
    MemberDetail save(MemberDetail memberDetail);
}
