package my.study.springecommercestudy.dto;


import my.study.springecommercestudy.domain.Member;

public class MyProfileResponse {
    private Long id;
    private String email;
    private String name;

    private MyProfileResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static MyProfileResponse of(Member member) {
        return new MyProfileResponse(member.getId(), member.getEmail(), member.getName());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
