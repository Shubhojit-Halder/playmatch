package com.playmatch.playmatch.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playmatch.playmatch.dto.CreateUserRequest;
import com.playmatch.playmatch.dto.PagedResponse;
import com.playmatch.playmatch.dto.UpdateUserRequest;
import com.playmatch.playmatch.dto.UserResponse;
import com.playmatch.playmatch.dto.booking.BookingResponse;
import com.playmatch.playmatch.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
     
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody  CreateUserRequest request) {
        log.info("Received request to create user: {}", request);
        UserResponse userResponse = userService.createUser(request);
        log.info("Returning after User created successfully from createUser method of UserController: {}", request);
        return userResponse;
    }

    // @GetMapping
    // public List<UserResponse> findAllUsers() {
    //     log.info("Received request to find all users");
    //     List<UserResponse> userResponses = userService.findAllUsers();
    //     log.info("Returning list of all users");
    //     return userResponses;
    // }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping
    public PagedResponse getAllUsers(Pageable pageable) {
        log.info("Received request to get all users with pagination: {}", pageable);
        PagedResponse<UserResponse> userResponses = userService.getAllUsers(pageable);
        log.info("Returning paginated list of users");
        return userResponses;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable String id) {
        log.info("Received request to get user by ID: {}", id);
        UserResponse userResponse = userService.getUserById(id);
        log.info("Returning user with ID: {}", id);
        return userResponse;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request) {
        log.info("Received request to update user with ID: {}", id);
        UserResponse userResponse = userService.updateUser(id, request);
        log.info("Returning updated user with ID: {}", id);
        return userResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        log.info("Received request to delete user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User deleted with ID: {}", id);
    }

    @GetMapping("{userid}/bookings")
    public PagedResponse<BookingResponse> getBookingsByUserId(@PathVariable String userid,@PageableDefault(size=10, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable) {
        log.info("Received request to get bookings for user ID: {}, page: {}, size: {}", userid, pageable.getPageNumber(), pageable.getPageSize());
        PagedResponse<BookingResponse> bookings = userService.getBookingsByUserId(userid, pageable);
        log.info("Returning paginated bookings for user ID: {}", userid);
        return bookings;
    }
    
}
