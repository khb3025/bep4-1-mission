package com.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.back.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}