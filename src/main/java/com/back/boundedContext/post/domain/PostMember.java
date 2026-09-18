package com.back.boundedContext.post.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "POST_MEMBER")
public class PostMember extends BaseIdAndTime {

    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    private int activityScore;

}
