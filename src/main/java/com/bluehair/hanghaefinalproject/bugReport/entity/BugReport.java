package com.bluehair.hanghaefinalproject.bugReport.entity;


import com.bluehair.hanghaefinalproject.common.entity.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class BugReport extends Timestamped {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String contents;

    @Builder
    public BugReport(String nickname, String contents) {
        this.nickname = nickname;
        this.contents = contents;
    }
}
