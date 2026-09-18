package com.back.boundedContext.post.app;

import java.util.Optional;

import com.back.boundedContext.post.domain.PostMember;
import com.back.boundedContext.post.out.PostMemberRepository;
import com.back.global.RsData.RsData;
import com.back.shared.member.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.post.domain.Post;
import com.back.boundedContext.post.out.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostRepository postRepository;
    private final PostWriteUseCase postWriteUseCase;
    private final PostMemberRepository postMemberRepository;

    @Transactional (readOnly = true)
    public long count() {
        return postRepository.count();
    }

    @Transactional
    public RsData<Post> write(Member author, String title, String content) {
        return postWriteUseCase.write(author, title, content);
    }

    @Transactional(readOnly = true)
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Transactional
    public void syncMember(MemberDto member) {
        PostMember _member = new PostMember(
                member.getId(),
                member.getCreateDate(),
                member.getModifyDate(),
                member.getUsername(),
                "",
                member.getNickname()
        );

        postMemberRepository.save(_member);
    }
}