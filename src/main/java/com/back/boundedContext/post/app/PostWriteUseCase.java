package com.back.boundedContext.post.app;

import com.back.boundedContext.member.app.MemberFacade;
import com.back.boundedContext.post.domain.PostMember;
import com.back.global.RsData.RsData;
import com.back.shared.member.out.MemberApiClient;
import org.springframework.stereotype.Service;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.post.dto.PostDto;
import com.back.shared.post.event.PostCreatedEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@Service 
@RequiredArgsConstructor 
public class PostWriteUseCase {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;
    private final MemberApiClient memberApiClient;

    public RsData<Post> write(PostMember author, String title, String content) {
        Post post = postRepository.save(new Post(author, title, content));

        eventPublisher.publish(
                new PostCreatedEvent(
                        new PostDto(post)
                )
        );

        // String randomSecureTip = memberFacade.randomSecurityTip();
        // 14강 내용 수정
        String randomSecureTip = memberApiClient.getRandomSecureTip();
        return new RsData<>("201-1",
                                "%d 번의 글이 생성되었습니다. 보안 팁 : %s ".formatted(post.getId(),  randomSecureTip),
                            post);
    }
}