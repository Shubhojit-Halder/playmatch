package com.playmatch.playmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.playmatch.playmatch.entity.Booking;
import com.playmatch.playmatch.enums.BookingStatus;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findByUser_IdAndMatch_Id(String userId, String matchId);
    public boolean existsByUser_IdAndMatch_Id(String userId, String matchId);
    public long countByMatch_IdAndStatus(String matchId, BookingStatus status);
    Page<Booking> findByUser_Id(String userId, Pageable pageable);
}
