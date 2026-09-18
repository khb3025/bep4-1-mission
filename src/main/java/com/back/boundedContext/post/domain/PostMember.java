package com.back.boundedContext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "POST_MEMBER")
public class PostMember extends ReplicaMember {

   public PostMember(String username, String password, String nickname) {
        super(username, password, nickname);
    }
}
