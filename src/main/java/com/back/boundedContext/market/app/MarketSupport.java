package com.back.boundedContext.market.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.Cash.dto.CashMemberDto;
import com.back.shared.Cash.event.CashMemberCreateEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketSupport {

    private final MarketMemberRepository marketMemberRepository;
    private final EventPublisher eventPublisher;


}
