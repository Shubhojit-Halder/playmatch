package com.playmatch.playmatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playmatch.playmatch.dto.booking.BookingRequest;
import com.playmatch.playmatch.dto.booking.BookingResponse;
import com.playmatch.playmatch.service.BookingService;
import com.playmatch.playmatch.dto.PagedResponse;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@Slf4j
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        // Implement the logic to create a booking
        log.info("Received request to create a booking");
        BookingResponse bookingResponse = bookingService.createBooking(request);
        log.info("Returning after Booking created successfully from createBooking method of BookingController");
        return ResponseEntity.status(201).body(bookingResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable String id) {
        log.info("Received request to get booking by ID: {}", id);
        BookingResponse bookingResponse = bookingService.getBookingById(id);
        log.info("Returning booking details for ID: {}", id);
        return ResponseEntity.ok(bookingResponse);
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable String bookingId) {
        BookingResponse bookingResponse = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(bookingResponse);
    }
}