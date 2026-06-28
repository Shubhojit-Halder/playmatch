package com.playmatch.playmatch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.playmatch.playmatch.dto.CreateUserRequest;
import com.playmatch.playmatch.dto.UserResponse;
import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.exception.EmailAlreadyExistsException;
import com.playmatch.playmatch.exception.UserNotFoundException;
import com.playmatch.playmatch.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public UserResponse createUser(CreateUserRequest user) {
        log.info("Creating user with email: {}", user.email());
        if (userRepository.existsByEmail(user.email())) {
            log.error("Email already in use: {}", user.email());
            throw new EmailAlreadyExistsException("Email already in use");  
        }
        User userEntity = new User();
        userEntity.setName(user.name());
        userEntity.setEmail(user.email());
        userEntity.setRating(1200);
        User savedUser = userRepository.save(userEntity);
        log.info("User created with ID: {}", savedUser.getId());
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRating()
        );
    }  
    
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRating()
                ))
                .toList();
    }

    public UserResponse getUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRating()
                ))
                .orElseThrow(() ->
                    new UserNotFoundException(id));
    }
}
