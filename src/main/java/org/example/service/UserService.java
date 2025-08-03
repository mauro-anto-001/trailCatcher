package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service // Tell Spring this is business logic, not controller or model
public class UserService {
    private final UserRepository userRepository;

    @Autowired //Inject the UserRepository into the service
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Create or save a user including validation, logging, etc
    public User createUser(User user) {
        return userRepository.save(user);
    }
    // Get a user by ID
    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }
    // Get a user by email (custom method)
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
