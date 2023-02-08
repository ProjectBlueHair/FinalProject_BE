package com.bluehair.hanghaefinalproject.post.repository;

import org.springframework.stereotype.Component;

@Component
public interface PostRepository extends PostCustomRepository, JPAPostRepository {

}
