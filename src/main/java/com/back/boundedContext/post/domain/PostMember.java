package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import com.back.global.jpa.entity.BaseIdAndTimeManual;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "POST_MEMBER")
public class PostMember extends BaseIdAndTimeManual {

    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;


}
