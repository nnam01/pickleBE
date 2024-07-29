package com.pickle.pickle_BE.service;

import com.pickle.pickle_BE.dto.request.RegisterUserRequest;
import com.pickle.pickle_BE.dto.response.RegisterUserResponse;
import com.pickle.pickle_BE.entity.User;
import com.pickle.pickle_BE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                bCryptPasswordEncoder.encode(request.getPassword()),
                request.getPhoneNumber()
        );
        userRepository.save(user);
        return new RegisterUserResponse(user.getUserId(), user.getEmail(), user.getName());
    }
}
