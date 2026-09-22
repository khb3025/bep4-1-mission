package com.back.boundedContext.member.app;


import com.back.global.RsData.RsData;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.member.event.MemberJoinedEvent;
import org.springframework.stereotype.Service;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.global.exception.DomainException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberJoinUseCase {
    private final MemberRepository memberRepository;
    private final EventPublisher eventPublisher;

    public RsData<Member> join(String username, String password, String nickname) {
        memberRepository.findByUsername(username).ifPresent(m -> {
            throw new DomainException("409-1", "이미 존재하는 username 입니다.");
        });
        Member joinMember = new Member(username, password, nickname);
        memberRepository.save(joinMember);
        eventPublisher.publish(new MemberJoinedEvent(joinMember.toDto()));
        return new RsData<>("200-1", "%s님의 회원가입을 환영합니다.".formatted(joinMember.getUsername()), joinMember );
    }
}