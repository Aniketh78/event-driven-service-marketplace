package com.booking_micro.bookingService.repository;

import com.booking_micro.bookingService.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> findByUserId(Long userId);
    Booking findByIdAndUserId(Long id, Long userId);

}
