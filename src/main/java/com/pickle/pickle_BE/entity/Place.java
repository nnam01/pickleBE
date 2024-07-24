package com.pickle.pickle_BE.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "places")
@Data
@NoArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "place_name", length = 50, nullable = false)
    private String placeName;

    @Column(name = "type", length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructor to initialize Place with all required fields
    public Place(String address, String placeName, String type, User user) {
        this.address = address;
        this.placeName = placeName;
        this.type = type;
        this.user = user;
    }
}
