package org.example.dto;
public class LoginResponse {
    public String token;        // access token (JWT)
    public String tokenType="Bearer";
    public String refreshToken; // long-lived
    public LoginResponse(String token, String refreshToken){ this.token=token; this.refreshToken=refreshToken; }
}
