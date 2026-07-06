package com.playmatch.playmatch.dto.booking;
import java.time.LocalDateTime;
import com.playmatch.playmatch.enums.BookingStatus;

public record BookingResponse(

    String bookingId,

    String userId,

    String userName,

    String matchId,

    String matchTitle,

    BookingStatus status,

    LocalDateTime createdAt

) {}
