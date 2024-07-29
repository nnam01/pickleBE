package com.pickle.pickle_BE.service;

import com.pickle.pickle_BE.entity.Place;
import com.pickle.pickle_BE.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // Place 저장
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    // Place ID로 검색
    public Place getPlaceById(Long id) {
        return placeRepository.findById(id).orElse(null);
    }

    // 모든 Place 조회
    public Iterable<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    // Place 삭제
    public void deletePlace(Long id) {
        placeRepository.deleteById(id);
    }
}
