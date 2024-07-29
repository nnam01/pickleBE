package com.pickle.pickle_BE.repository;

import com.pickle.pickle_BE.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")  // "test" 프로파일 활성화
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("모든 유저 조회")
    void testFindAllUsers() {
        // Given
        User user1 = User.builder()
                .name("김길동")
                .email("Kimkde@example.com")
                .password("securepassword")
                .phoneNumber("01004567890")
                .build();

        User user2 = User.builder()
                .name("김철수")
                .email("KIM@email.com")
                .password("securepassword")
                .phoneNumber("01001237890")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertThat(users)
                .extracting(User::getName)
                .contains(user1.getName(), user2.getName());
    }

    @Test
    @DisplayName("유저 생성 및 검색 성공")
    void testCreateAndFindUser() {
        // Given
        User user = User.builder()
                .name("홍길동")
                .email("HONG@example.com")
                .password("securepassword")
                .phoneNumber("01012307890")
                .build();

        // When
        userRepository.save(user);
        User foundUser = userRepository.findById(user.getUserId()).orElse(null);

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("홍길동");
        assertThat(foundUser.getEmail()).isEqualTo("HONG@example.com");
    }

    @Test
    @DisplayName("존재하지 않는 유저 검색")
    void testFindNonExistentUser() {
        // Given
        String nonExistentUserId = "non-existent-id";

        // When
        User foundUser = userRepository.findById(nonExistentUserId).orElse(null);

        // Then
        assertThat(foundUser).isNull();
    }

    @Test
    @DisplayName("유저 정보 업데이트 성공")
    void testUpdateUser() {
        // Given
        User user = User.builder()
                .name("김철수")
                .email("KIM@email.com")
                .password("securepassword")
                .phoneNumber("01041237890")
                .build();

        String userId = user.getUserId();
        userRepository.save(user);

        // When
        User foundUser = userRepository.findById(userId).orElse(null);
        assertThat(foundUser).isNotNull();

        // Update the user
        foundUser.setName("업데이트된 이름");
        userRepository.save(foundUser);

        // Then
        User updatedUser = userRepository.findById(userId).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("업데이트된 이름");
    }

    @Test
    @DisplayName("유저 생성 및 등록 날짜 확인")
    void testCreateUserAndRegisterDate() {
        // Given
        User user = User.builder()
                .name("김철수")
                .email("KIM@email.com")
                .password("securepassword")
                .phoneNumber("01012397890")
                .build();

        // When
        userRepository.save(user);
        User foundUser = userRepository.findById(user.getUserId()).orElse(null);

        // Print user details
        if (foundUser != null) {
            System.out.println("User ID: " + foundUser.getUserId());
            System.out.println("Name: " + foundUser.getName());
            System.out.println("Email: " + foundUser.getEmail());
            System.out.println("Password: " + foundUser.getPassword());
            System.out.println("Phone Number: " + foundUser.getPhoneNumber());
            System.out.println("Register Date: " + foundUser.getRegisterDate());
        }

        // Then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getRegisterDate()).isNotNull();

        // 확인을 위해 현재 시간과의 차이를 확인
        assertThat(Duration.between(foundUser.getRegisterDate(), LocalDateTime.now()).toMinutes()).isLessThan(1);
    }
}
