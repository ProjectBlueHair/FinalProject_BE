package com.bluehair.hanghaefinalproject.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="MEMBER_DETAIL_ID")
    private MemberDetail memberDetail;

    @Builder
    public Job(String name, MemberDetail memberDetail) {
        this.name = name;
        this.memberDetail = memberDetail;

        this.memberDetail.getJobList().add(this);
    }
}
