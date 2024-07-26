package com.pickle.pickle_BE.controller;

import com.pickle.pickle_BE.dto.request.RegisterUserRequest;
import com.pickle.pickle_BE.dto.response.RegisterUserResponse;
import com.pickle.pickle_BE.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }
}
