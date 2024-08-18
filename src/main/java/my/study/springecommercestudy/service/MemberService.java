package my.study.springecommercestudy.service;

import my.study.springecommercestudy.domain.Member;
import my.study.springecommercestudy.dto.JoinRequest;
import my.study.springecommercestudy.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(JoinRequest joinRequest) {
        Member createMember = memberRepository.save(joinRequest.toEntity());
        return createMember.getId();
    }
}
