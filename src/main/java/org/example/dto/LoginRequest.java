package org.example.dto;

import jakarta.validation.constraints.*;

//login info
public class LoginRequest {
    @Email @NotBlank public String email;
    @NotBlank public String password;
}
