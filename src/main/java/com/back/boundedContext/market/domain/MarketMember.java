package com.back.boundedContext.market.domain;

import com.back.shared.market.dto.MarketMemberDto;
import com.back.shared.member.domain.ReplicaMember;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "MARKET_MEMBER")
public class MarketMember extends ReplicaMember {
    public MarketMember(
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        String username,
        String nickname,
        String password,
        int activityScore
    ) {
        super(
            id,
            createDate,
            modifyDate,
            username,
            "",
            nickname,
            activityScore
        );
    }

    public MarketMemberDto toDto() {
        return new MarketMemberDto(
            this.getId(),
            this.getCreateDate(),
            this.getModifyDate(),
            this.getUsername(),
            this.getNickname(),
            this.getActivityScore()
        );
    }
}
