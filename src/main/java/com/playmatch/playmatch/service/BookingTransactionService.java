package com.playmatch.playmatch.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.playmatch.playmatch.dto.booking.BookingResponse;
import com.playmatch.playmatch.entity.Booking;
import com.playmatch.playmatch.exception.DuplicateBookingException;
import com.playmatch.playmatch.exception.MatchFullException;
import com.playmatch.playmatch.exception.MatchNotFoundException;
import com.playmatch.playmatch.exception.UserNotFoundException;
import com.playmatch.playmatch.dto.booking.BookingRequest;
import com.playmatch.playmatch.util.BookingResponseMapper;
import com.playmatch.playmatch.entity.Match;
import com.playmatch.playmatch.entity.User;
import com.playmatch.playmatch.enums.BookingStatus;
import com.playmatch.playmatch.repository.BookingRepository;
import com.playmatch.playmatch.repository.MatchRepository;
import com.playmatch.playmatch.repository.UserRepository;
import jakarta.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookingTransactionService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final BookingResponseMapper bookingResponseMapper;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        User user=userRepository.findById(request.userId()).orElseThrow(() -> new UserNotFoundException(request.userId())); // Implement this method to get the current user
        Match match = matchRepository.findById(request.matchId()).orElseThrow(() -> new MatchNotFoundException(request.matchId())); // Implement this method to get the match

        // Check if the user has already booked this match
        if (bookingRepository.existsByUser_IdAndMatch_Id(request.userId(), request.matchId())) {
            log.error("User {} has already booked match {}", request.userId(), request.matchId());
            throw new DuplicateBookingException("User has already booked this match");
        }

        if (match.getBookedPlayers() >= match.getMaxPlayers()) {
            log.error("Match {} is full. Current players: {}, Max players: {}", match.getTitle(), match.getBookedPlayers(), match.getMaxPlayers());
            throw new MatchFullException("Match is full");
        }

        log.info("Updating current players for match {}. Current players: {}, Max players: {}", match.getTitle(), match.getBookedPlayers(), match.getMaxPlayers());
        match.setBookedPlayers(match.getBookedPlayers() + 1);
        log.info("Match {} updated successfully. New current players: {}", match.getTitle(), match.getBookedPlayers());
        log.info("Creating booking for user {} and match {}", request.userId(), request.matchId());
        Booking matchBooking = new Booking();
        matchBooking.setUser(user);
        matchBooking.setMatch(match);
        matchBooking.setStatus(BookingStatus.CONFIRMED);
        matchBooking = bookingRepository.save(matchBooking);

        log.info("Booking created with ID: {}", matchBooking.getId());
        return bookingResponseMapper.toBookingResponse(matchBooking);    
    }
}
