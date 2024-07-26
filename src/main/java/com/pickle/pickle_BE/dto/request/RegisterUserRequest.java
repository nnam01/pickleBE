package com.pickle.pickle_BE.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
}