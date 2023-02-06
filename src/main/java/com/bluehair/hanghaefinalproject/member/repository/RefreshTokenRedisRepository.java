package com.bluehair.hanghaefinalproject.member.repository;

import com.bluehair.hanghaefinalproject.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

}
