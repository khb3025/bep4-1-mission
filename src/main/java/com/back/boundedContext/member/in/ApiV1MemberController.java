package com.back.boundedContext.member.in;

import com.back.boundedContext.member.app.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member/members")
public class ApiV1MemberController {

    private final MemberFacade memberFacade;

    @GetMapping("/randomSecureTip")
    public String getRandomSecureTip() {
        return memberFacade.getRandomSecurityTip();
    }
}
