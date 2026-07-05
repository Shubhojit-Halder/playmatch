package com.playmatch.playmatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
    public boolean existsByUserIdAndMatchId(String userId, String matchId);
    
}
