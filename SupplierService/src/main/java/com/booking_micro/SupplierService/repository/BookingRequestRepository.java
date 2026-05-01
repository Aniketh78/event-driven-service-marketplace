package com.booking_micro.SupplierService.repository;

import com.booking_micro.SupplierService.model.BookingRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRequestRepository extends JpaRepository<BookingRequestEntity, Long> {
    List<BookingRequestEntity> findByStatus(String status);
    Optional<BookingRequestEntity> findByBookingId(Long bookingId);
}