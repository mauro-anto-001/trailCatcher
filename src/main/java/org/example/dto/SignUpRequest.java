package org.example.dto;

import jakarta.validation.constraints.*;

//only want to send the data we need not the full user object which
// has stuff like id, unit preferences, etc.
public class SignUpRequest {
    @NotBlank
    public String name;
    @Email @NotBlank
    public String email;
    @Size(min = 8, message="Password must be at least 8 chars") @NotBlank public String password;
}
