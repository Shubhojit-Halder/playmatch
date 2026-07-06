package com.playmatch.playmatch.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String id) {
        super("Booking not found with ID: " + id);
    }
    
}
