package com.pickle.pickle_BE.repository;

import com.pickle.pickle_BE.entity.Place;
import com.pickle.pickle_BE.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Place 저장 및 조회 테스트")
    void testSaveAndFindPlace() {
        // Given
        User user = new User("홍길동", "test@example.com", "password", "01012345678");
        userRepository.save(user);
        Place place = new Place("서울시 강남구 테헤란로 311, 304호", "ㅇㅇ일식당", "추천", user);
        placeRepository.save(place);

        // When
        Place foundPlace = placeRepository.findById(place.getPlaceId()).orElse(null);

        // Then
        assertThat(foundPlace).isNotNull();
        assertThat(foundPlace.getPlaceName()).isEqualTo("ㅇㅇ일식당");
        assertThat(foundPlace.getUser()).isEqualTo(user);
    }
}
