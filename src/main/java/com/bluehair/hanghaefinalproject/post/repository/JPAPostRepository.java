package com.bluehair.hanghaefinalproject.post.repository;

import com.bluehair.hanghaefinalproject.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAPostRepository extends JpaRepository<Post, Long>, PostRepository {

}
