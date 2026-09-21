package com.back.shared.market.dto;

import com.back.boundedContext.market.domain.MarketMember;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor(
        onConstructor_ = @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
)
@Getter
public class MarketMemberDto {
    private final int id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String username;
    private String nickname;
    private int activityScore;

    public MarketMemberDto(
            MarketMember marketMember
    ){
        this.id = marketMember.getId();
        this.createDate = marketMember.getCreateDate();
        this.modifyDate = marketMember.getModifyDate();
        this.username = marketMember.getUsername();
        this.nickname = marketMember.getNickname();
        this.activityScore = marketMember.getActivityScore();
    }
}
