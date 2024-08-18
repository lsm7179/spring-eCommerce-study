package my.study.springecommercestudy.controller;

import my.study.springecommercestudy.dto.JoinRequest;
import my.study.springecommercestudy.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody final JoinRequest joinRequest) {
        Long memberId = memberService.join(joinRequest);
        return ResponseEntity
                .created(URI.create("/login"))
                .body(memberId);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(String username) {
        return ResponseEntity.ok("token");
    }


    // get 아무거나 해서 테스트
}
