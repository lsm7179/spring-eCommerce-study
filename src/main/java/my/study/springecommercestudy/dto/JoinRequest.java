package my.study.springecommercestudy.dto;

import my.study.springecommercestudy.domain.Member;

public class JoinRequest {

    private String email;
    private String password;
    private String name;

    public JoinRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member toEntity() {
        return new Member(email, password, name);
    }
}
