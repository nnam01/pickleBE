package com.pickle.pickle_BE.repository;

import com.pickle.pickle_BE.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
