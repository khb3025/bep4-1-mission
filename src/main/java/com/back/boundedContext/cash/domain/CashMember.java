package com.back.boundedContext.cash.domain;

import com.back.shared.Cash.dto.CashMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import com.back.shared.member.dto.MemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CASH_MEMBER")
public class CashMember extends ReplicaMember {

    public CashMember(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String username,
        String password,
        String nickname,
        int activityScore
    ) {
        super(id, createDate, modifyDate, username, password, nickname, activityScore);

    }

    public CashMemberDto toDto(){
        return new CashMemberDto(
                this.getId(),
                this.getCreateDate(),
                this.getModifyDate(),
                this.getUsername(),
                this.getNickname(),
                this.getActivityScore()
        );
    }
}
