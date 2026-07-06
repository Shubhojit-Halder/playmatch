package com.playmatch.playmatch.service;
import org.springframework.stereotype.Service;

import com.playmatch.playmatch.dto.booking.BookingRequest;
import com.playmatch.playmatch.dto.booking.BookingResponse;
import com.playmatch.playmatch.entity.Booking;
import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.enums.BookingStatus;
import com.playmatch.playmatch.service.BookingTransactionService;
import com.playmatch.playmatch.exception.BookingNotFoundException;
import com.playmatch.playmatch.exception.DuplicateBookingException;
import com.playmatch.playmatch.exception.BookingCannotBeCancelledException;
import com.playmatch.playmatch.exception.MatchNotFoundException;
import com.playmatch.playmatch.exception.UserNotFoundException;
import com.playmatch.playmatch.exception.MatchFullException;
import com.playmatch.playmatch.repository.BookingRepository;
import com.playmatch.playmatch.repository.MatchRepository;
import com.playmatch.playmatch.repository.UserRepository;
import com.playmatch.playmatch.util.BookingResponseMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final BookingResponseMapper bookingResponseMapper;
    private final BookingTransactionService bookingTransactionService;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        // Implement the logic to create a booking
        log.info("Creating a new booking");
        for(int i=0;i<3;i++){
            try {
                return bookingTransactionService.createBooking(request);
            } catch (DuplicateBookingException | MatchFullException e) {
                log.error("Attempt {}: {}", i + 1, e.getMessage()); 
                throw e; // Rethrow the exception to be handled by the controller
            } catch (ObjectOptimisticLockingFailureException e) {
                log.error("Attempt {} failed due to an unexpected error: {}", i + 1, e.getMessage());
                try{
                    Thread.sleep(100 * (i+1));
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException("Failed to create booking after multiple attempts due to concurrent updates. Please try again.");
    }

    public BookingResponse getBookingById(String id) throws BookingNotFoundException {
        log.info("Fetching booking with ID: {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));

        return bookingResponseMapper.toBookingResponse(booking);
    }

    @Transactional
    public BookingResponse cancelBooking(String bookingId) {
        log.info("Cancelling booking with ID: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));

        Match match = booking.getMatch();

        if(match.getMatchTime().isBefore(java.time.LocalDateTime.now())) {
            log.error("Cannot cancel booking {} for match {} as the match has already started", bookingId, booking.getMatch().getId());
            throw new BookingCannotBeCancelledException("Cannot cancel booking for a match that has already started");
        }
        if(booking.getStatus() == BookingStatus.CANCELLED) {
            log.error("Booking {} is already cancelled", bookingId);
            throw new BookingCannotBeCancelledException("Booking is already cancelled");
        }

        log.info("Updating current players for match {}. Current players: {}, Max players: {}", match.getId(), match.getBookedPlayers(), match.getMaxPlayers());
        match.setBookedPlayers(match.getBookedPlayers() - 1);

        booking.setStatus(BookingStatus.CANCELLED);
        //not calling save method because of @Transactional annotation, it will automatically save the changes when the transaction is committed.
        log.info("Booking with ID: {} has been cancelled successfully", bookingId);
        return bookingResponseMapper.toBookingResponse(booking);
    }
}
