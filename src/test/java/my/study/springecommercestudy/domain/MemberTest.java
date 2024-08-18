package my.study.springecommercestudy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("멤버 생성 테스트")
    @Test
    void createMember() {
        Member member = new Member("test@test.com", "1234!", "이승민");

        assertThat(member.getEmail()).isEqualTo("test@test.com");
        assertThat(member.getPassword()).isEqualTo("1234!");
        assertThat(member.getName()).isEqualTo("이승민");
    }
}