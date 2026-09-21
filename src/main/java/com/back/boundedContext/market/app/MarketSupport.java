package com.back.boundedContext.market.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.boundedContext.market.out.ProductRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.Cash.dto.CashMemberDto;
import com.back.shared.Cash.event.CashMemberCreateEvent;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketSupport {

    private final ProductRepository productRepository;
    private final MarketMemberRepository marketMemberRepository;

    public long countProducts() {
        return productRepository.count();
    }

    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketMemberRepository.findByUsername(username);
    }
}
