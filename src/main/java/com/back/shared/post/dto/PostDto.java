package com.back.shared.post.dto;

import java.time.LocalDateTime;

import com.back.boundedContext.post.domain.Post;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

// * @JsonCreator
// Controller에서 반환하는건 Dto는 문제없음
// ApiClient에서 받을때는 Json 역직렬화가 필요해서 붙임

@AllArgsConstructor
@Getter
public class PostDto {
    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final int authorId;
    private final String authorName;
    private final String title;
    private final String content;

}
