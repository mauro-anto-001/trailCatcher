package org.example.controller;

import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.dto.SignUpRequest;
import org.example.model.User;
import org.example.service.AuthService;
import org.example.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CurrentUserService currentUser;
    public AuthController(AuthService authService, CurrentUserService currentUser){
        this.authService = authService;
        this.currentUser = currentUser;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody @jakarta.validation.Valid SignUpRequest request) {
        User newUser = authService.registerUser(request);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @jakarta.validation.Valid LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    // NEW: who am I?
    @GetMapping("/me")
    public ResponseEntity<User> me() {
        return ResponseEntity.ok(currentUser.requireCurrentUser());
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody java.util.Map<String,String> body){
        return ResponseEntity.ok(authService.refresh(body.get("refreshToken")));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        var me = currentUser.requireCurrentUser();
        authService.logoutAll(me);
        return ResponseEntity.ok().build();
    }

}
