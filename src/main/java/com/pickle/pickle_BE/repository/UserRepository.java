package com.pickle.pickle_BE.repository;

import com.pickle.pickle_BE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
}
