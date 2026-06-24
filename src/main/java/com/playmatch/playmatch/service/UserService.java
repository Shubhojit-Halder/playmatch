package com.playmatch.playmatch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if(user.getRating() == null) {
            user.setRating(1200);
        }
        return userRepository.save(user);
    }  
    
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));
    }
}
