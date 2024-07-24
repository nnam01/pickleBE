package com.pickle.pickle_BE.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", length = 36, nullable = false)
    private String userId;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 11, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "register_date", nullable = false)
    private LocalDateTime registerDate;

    // Constructor to create a new user with UUID as String
    public User(String name, String email, String password, String phoneNumber) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.registerDate = LocalDateTime.now();
    }
}
