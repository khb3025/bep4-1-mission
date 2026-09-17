package com.back.boundedContext.member.in;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.member.domain.Member;
import com.back.shared.post.event.PostCommentCreatedEvent;
import com.back.shared.post.event.PostCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class MemberEventListener {

    private final MemberFacade memberFacade;

    // 글 작성 점수 3점을 올린다.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional (propagation = Propagation.REQUIRES_NEW)
    public void handle(PostCreatedEvent event){
        Member member = memberFacade.findById(event.getPost().getAuthorId()).get();
        member.increaseActivityScore(3);
    }

    // 댓글 작성시 점수 1점을 올리다.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional (propagation = Propagation.REQUIRES_NEW)
    public void handle(PostCommentCreatedEvent event){
        Member member = memberFacade.findById(event.getPostComment().getAuthorId()).get();
        member.increaseActivityScore(1);
    }
}
