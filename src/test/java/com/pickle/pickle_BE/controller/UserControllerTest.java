package com.pickle.pickle_BE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pickle.pickle_BE.dto.request.RegisterUserRequest;
import com.pickle.pickle_BE.dto.response.RegisterUserResponse;
import com.pickle.pickle_BE.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 가입 테스트")
    void testRegisterUser() throws Exception {
        // 요청 데이터
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("user@example.com");
        request.setPassword("password123");
        request.setName("홍길동");
        request.setPhoneNumber("01012345678");

        // 응답 데이터
        String userId = UUID.randomUUID().toString();
        RegisterUserResponse response = new RegisterUserResponse(userId, request.getEmail(), request.getName());

        // UserService의 mock 설정
        when(userService.registerUser(request)).thenReturn(response);

        // 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // CSRF 토큰 추가
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(request.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.getName()));
        System.out.println("User ID: " + userId);
        System.out.println("Password: " + request.getPassword());
    }

    @Test
    @DisplayName("유효하지 않은 데이터값으로 요청")
    void testRegisterUserWithInvalidData() throws Exception {
        // 유효하지 않은 요청 데이터
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail(""); // 빈 이메일
        request.setPassword("short"); // 짧은 비밀번호
        request.setName(""); // 빈 이름
        request.setPhoneNumber(""); // 빈 전화번호

        // 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // CSRF 토큰 추가
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("Email cannot be empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("Password must be at least 8 characters long"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Name cannot be empty"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("Phone number cannot be empty"));
    }

    @Test
    @DisplayName("이메일")
    void testRegisterUserWithDuplicateEmail() throws Exception {
        // 요청 데이터
        RegisterUserRequest request = new RegisterUserRequest();
        request.setEmail("duplicate@example.com");
        request.setPassword("password123");
        request.setName("홍길동");
        request.setPhoneNumber("01012345678");

        // 중복된 이메일에 대한 UserService의 mock 설정
        when(userService.registerUser(request)).thenThrow(new RuntimeException("Email already exists"));

        // 요청 및 응답 검증
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // CSRF 토큰 추가
                .andExpect(MockMvcResultMatchers.status().isConflict()) // 중복 이메일로 Conflict 상태
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Email already exists"));
    }

}
