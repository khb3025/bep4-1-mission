package com.back.boundedContext.post.in;

import com.back.boundedContext.post.app.PostFacade;
import com.back.boundedContext.post.domain.Post;
import com.back.shared.post.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post/posts")
public class ApiV1PostController {

    private final PostFacade postFacade;


    // TDD 할 때  유용해서 교안에서 Transactional을 붙임
    @GetMapping("")
    @Transactional(readOnly = true)
    public List<PostDto> getItems() {
        return postFacade
                .findByOrderByIdDesc()
                .stream()
                .map(Post::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PostDto getItem(
            @PathVariable int id
    ) {
        return postFacade
                .findById(id)
                .map(Post::toDto)
                .get();
    }
}
