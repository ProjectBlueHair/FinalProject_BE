package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.repository.Custom.JobCustomRepository;
import com.bluehair.hanghaefinalproject.member.repository.JPA.JPAJobRepository;

public interface JobRepository extends JPAJobRepository, JobCustomRepository {

}
