package com.back.boundedContext.member.app;

import java.util.Optional;

import com.back.boundedContext.member.domain.MemberPolicy;
import com.back.global.RsData.RsData;
import org.springframework.stereotype.Service;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service 
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFacade {
    private final MemberSupport memberSupport;
    private final MemberJoinUseCase memberJoinUseCase;
    private final MemberGetRandomSecureTipUseCase memberGetRandomSecureTipUseCase;

    public long count() {
        return memberSupport.count();
    }

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        return memberJoinUseCase.join(username, password, nickname);
    }

    public Optional<Member> findByUsername(String username) {
        return memberSupport.findByUsername(username);
    }

    public Optional<Member> findById(int id) {
        return memberSupport.findById(id);
    }

    public String getRandomSecurityTip(){
        return memberGetRandomSecureTipUseCase.getRandomSecurityTip();
    }
}
