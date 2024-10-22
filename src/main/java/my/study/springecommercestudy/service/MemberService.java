package my.study.springecommercestudy.service;

import my.study.springecommercestudy.domain.Member;
import my.study.springecommercestudy.dto.JoinRequest;
import my.study.springecommercestudy.dto.LoginRequest;
import my.study.springecommercestudy.repository.MemberRepository;
import my.study.springecommercestudy.config.jwt.JwtGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtGenerator jwtGenerator;

    public MemberService(MemberRepository memberRepository, JwtGenerator jwtGenerator) {
        this.memberRepository = memberRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @Transactional
    public Long join(JoinRequest joinRequest) {
        Member createMember = memberRepository.save(joinRequest.toEntity());
        return createMember.getId();
    }


    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));

        return jwtGenerator.generateToken(member);
    }
}
