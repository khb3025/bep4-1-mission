package com.back.boundedContext.post.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.post.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByOrderByIdDesc();
}