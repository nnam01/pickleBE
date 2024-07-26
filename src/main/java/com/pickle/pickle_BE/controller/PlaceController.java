package com.pickle.pickle_BE.controller;

import com.pickle.pickle_BE.entity.Place;
import com.pickle.pickle_BE.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @PostMapping
    public ResponseEntity<Place> createPlace(@RequestBody Place place) {
        Place savedPlace = placeService.savePlace(place);
        return new ResponseEntity<>(savedPlace, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlaceById(@PathVariable Long id) {
        Place place = placeService.getPlaceById(id);
        if (place != null) {
            return new ResponseEntity<>(place, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Place>> getAllPlaces() {
        return new ResponseEntity<>(placeService.getAllPlaces(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        placeService.deletePlace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}