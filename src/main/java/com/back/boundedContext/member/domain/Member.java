package com.back.boundedContext.member.domain;

import com.back.shared.member.domain.SourceMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "MEMBER_MEMBER")
public class Member extends SourceMember {

    public Member(String username, String password, String nickname) {
        super(username, password, nickname);
    }

    public int increaseActivityScore(int amount) {
        setActivityScore(getActivityScore()+amount);
        return getActivityScore();
    }
}