package my.study.springecommercestudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입")
    @Test
    @Order(0)
    void create() throws Exception {
        Map<String, String> joinRequest = new HashMap<>();

        joinRequest.put("email", "test@test.com");
        joinRequest.put("password", "1234!");
        joinRequest.put("name", "이승민");

        MvcResult mvcResult = mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertThat(response).isNotBlank();
    }

    @DisplayName("로그인")
    @Test
    @Order(1)
    void join() throws Exception {
        Map<String, String> joinRequest = new HashMap<>();

        joinRequest.put("email", "test@test.com");
        joinRequest.put("password", "1234!");

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertThat(response).isNotBlank();
    }
}