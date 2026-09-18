package com.back.boundedContext.post.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;
import com.back.global.RsData.RsData;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.member.out.MemberApiClient;
import com.back.shared.post.event.PostCreatedEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

// 외부 의존성(Repository, EventPublisher, MemberApiClient)을 모두 Mock으로 대체한
// 순수 단위 테스트라 Spring 컨텍스트 없이 빠르게 동작한다.
@ExtendWith(MockitoExtension.class)
class PostWriteUseCaseTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private EventPublisher eventPublisher;
    @Mock
    private MemberApiClient memberApiClient;

    @InjectMocks
    private PostWriteUseCase postWriteUseCase;

    private Member author;

    @BeforeEach
    void setUp() {
        author = new Member("khb", "1234", "케이비");
    }

    @Test
    void 글을_작성하면_저장되고_이벤트가_발행되고_보안팁이_포함된_응답을_반환한다() {
        // given
        given(postRepository.save(any(Post.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        given(memberApiClient.getRandomSecureTip())
                .willReturn("비밀번호는 주기적으로 변경하세요");

        // when
        RsData<Post> result = postWriteUseCase.write(author, "제목", "내용");

        // then
        assertThat(result.getResultCode()).isEqualTo("201-1");
        assertThat(result.getMsg()).contains("비밀번호는 주기적으로 변경하세요");
        assertThat(result.getData().getTitle()).isEqualTo("제목");
        assertThat(result.getData().getContent()).isEqualTo("내용");
        assertThat(result.getData().getAuthor()).isEqualTo(author);

        verify(postRepository).save(any(Post.class));

        ArgumentCaptor<PostCreatedEvent> eventCaptor = ArgumentCaptor.forClass(PostCreatedEvent.class);
        verify(eventPublisher).publish(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getPost().getTitle()).isEqualTo("제목");
    }
}
