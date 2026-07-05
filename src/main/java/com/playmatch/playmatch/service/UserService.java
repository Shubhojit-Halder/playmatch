package com.playmatch.playmatch.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.playmatch.playmatch.dto.CreateUserRequest;
import com.playmatch.playmatch.dto.PagedResponse;
import com.playmatch.playmatch.dto.UpdateUserRequest;
import com.playmatch.playmatch.dto.UserResponse;
import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.exception.EmailAlreadyExistsException;
import com.playmatch.playmatch.exception.UserNotFoundException;
import com.playmatch.playmatch.repository.UserRepository;
import com.playmatch.playmatch.util.PageMapper;

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
        log.info("Fetching all users from findAllUsers method of UserService");
        List<UserResponse> userResponses = userRepository.findAll().stream()
                .map(user -> new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRating()
        ))
                .toList();
        if (userResponses.isEmpty()) {
            log.warn("No users found in the database");
        } else {
            log.info("Found {} users in the database", userResponses.size());
        }
        log.info("Returning list of all users from findAllUsers method of UserService");
        return userResponses;
    }

    public UserResponse getUserById(String id) throws UserNotFoundException {
        log.info("Fetching user by ID from getUserById method of UserService: {}", id);
        UserResponse xUser = userRepository.findById(id)
                .map(user -> new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRating()
        ))
                .orElseThrow(()
                        -> new UserNotFoundException(id));
        log.info("Returning user with ID: {}", id);
        return xUser;
    }

    public UserResponse updateUser(String id, UpdateUserRequest req) throws UserNotFoundException {
        log.info("Updating user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        
        userRepository.findByEmail(req.email()).filter(u -> !u.getId().equals(id)).ifPresent(u -> {
            log.error("Email already in use: {}", req.email());
            throw new EmailAlreadyExistsException("Email already in use :"+ req.email());
        });

        user.setName(req.name());
        user.setEmail(req.email());

        User updatedUser = userRepository.save(user);
        log.info("User updated with ID: {}", updatedUser.getId());

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRating()
        );
    }

    public void deleteUser(String id) throws UserNotFoundException {
        log.info("Deleting user with ID: {}", id);
        if (!userRepository.existsById(id)) {
            log.error("User not found with ID: {}", id);
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        log.info("User deleted with ID: {}", id);
    }

    public PagedResponse getAllUsers(Pageable pageable) {
        pageable = Pageable.ofSize(Math.min(pageable.getPageSize(), 100)); // Limit page size to 100

        log.info("Fetching all users with pagination from getAllUsers method of UserService");
        
        Page<UserResponse> usersPage = userRepository.findAll(pageable)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRating()
                ));
        // List<UserResponse> users = usersPage.toList();
        
        if (usersPage.isEmpty()) {
            log.warn("No users found in the database");
        } else {
            log.info("Found {} users in the database", usersPage.getTotalElements());
        }
        log.info("Returning paginated list of users from getAllUsers method of UserService");
        return PageMapper.from(usersPage);
    }
}
