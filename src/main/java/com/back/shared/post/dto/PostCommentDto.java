package com.back.shared.post.dto;

import java.time.LocalDateTime;

import com.back.boundedContext.post.domain.PostComment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor 
@Getter 
public class PostCommentDto {

    private final int id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final int postId;
    private final int authorId;
    private final String authorName;
    private final String content;

}
