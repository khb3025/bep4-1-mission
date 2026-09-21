package com.back.shared.market.event;


import com.back.shared.market.dto.MarketMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@AllArgsConstructor
public class MarketMemberCreatedEvent {
    private MarketMemberDto member;
}
