package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository users;
    public CurrentUserService(UserRepository users){ this.users = users; }

    public User requireCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) throw new RuntimeException("Unauthenticated");
        String email = auth.getName(); // we stored email as subject
        return users.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
