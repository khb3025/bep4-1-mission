package com.back.boundedContext.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.boundedContext.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}