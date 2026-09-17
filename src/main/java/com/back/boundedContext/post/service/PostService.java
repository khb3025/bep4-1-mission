package com.back.boundedContext.post.service;
import org.springframework.stereotype.Service;

import com.back.boundedContext.post.entity.Post;
import com.back.boundedContext.post.repository.PostRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;
import com.back.boundedContext.member.entity.Member;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;
    public long count() {
        return postRepository.count();
    }

    public Post write(Member author, String title, String content) {
        Post post = postRepository.save(new Post(author, title, content));
        //author.increaseActivityScore(3);
        eventPublisher.publish(
            new PostCreatedEvent(
                new PostDto(post)
            )
        );
        return postRepository.save(post);
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }
}