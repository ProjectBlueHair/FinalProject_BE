package com.bluehair.hanghaefinalproject.member.repository.Custom;

import com.bluehair.hanghaefinalproject.member.entity.MemberDetail;
import org.springframework.data.repository.query.Param;

public interface JobCustomRepository {
    void deleteAllByMemberDetail(@Param("MemberDetail") MemberDetail memberDetail);
}
