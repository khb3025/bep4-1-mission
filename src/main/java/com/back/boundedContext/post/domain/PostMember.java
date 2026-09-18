package com.back.boundedContext.post.domain;

import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "POST_MEMBER")
public class PostMember extends ReplicaMember {

   public PostMember(
       int id,
       LocalDateTime createDate,
       LocalDateTime modifyDate,
       String username,
       String password,
       String nickname
   ) {
        super(id, createDate, modifyDate,username, password, nickname);
    }
}
