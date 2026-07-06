package com.playmatch.playmatch.util;

import com.playmatch.playmatch.dto.booking.BookingResponse;
import com.playmatch.playmatch.entity.Booking;
import org.springframework.stereotype.Component;
@Component
public class BookingResponseMapper {

    public BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getUser().getName(),
                booking.getMatch().getId(),
                booking.getMatch().getTitle(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }
}
