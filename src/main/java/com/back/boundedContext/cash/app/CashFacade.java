package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.cash.out.CashMemberRepository;
import com.back.boundedContext.cash.out.WalletRepository;
import com.back.shared.member.dto.MemberDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashFacade {
    private final CashMemberRepository cashMemberRepository;
    private final WalletRepository walletRepository;

    public CashMember syncMember(MemberDto member) {
        CashMember _member = new CashMember(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                "",
                member.getNickname(),
                member.getActivityScore()
        );

        return cashMemberRepository.save(_member);
    }

    @Transactional
    public Wallet createWallet(CashMember holder) {
        Wallet wallet = new Wallet(holder);
        return walletRepository.save(wallet);
    }
}
