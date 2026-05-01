package com.booking_micro.bookingService.service;

import com.booking_micro.bookingService.config.UserPrincipal;
import com.booking_micro.bookingService.constants.BookingEntityCons;
import com.booking_micro.bookingService.dto.BookingRequestDto;
import com.booking_micro.bookingService.dto.UserDetailsDto;
import com.booking_micro.bookingService.entity.Booking;
import com.booking_micro.bookingService.producer.BookingProducer;
import com.booking_micro.bookingService.repository.BookingRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final IdentityService identityService;
    private final BookingProducer bookingProducer;

    public BookingService(BookingRepository bookingRepository, IdentityService identityService, BookingProducer bookingProducer) {
        this.bookingRepository = bookingRepository;
        this.identityService = identityService;
        this.bookingProducer = bookingProducer;
    }

    @PreAuthorize("hasRole('USER')")
    public String CreateBooking(BookingRequestDto bookingRequestDto) {
        UserDetailsDto user = identityService.getAuthDetails();
        Long UserId = user.id();

        Booking booking = new Booking();
        booking.setUserId(UserId);
        booking.setType(bookingRequestDto.getType());
        booking.setStatus(BookingEntityCons.CREATED);
        bookingRepository.save(booking);

        bookingProducer.sendBooking(booking);

        return "Booking Created";
    }

    @PreAuthorize("hasRole('USER')")
    public List<Booking> getMyBooking() {
        UserDetailsDto user = identityService.getAuthDetails();
        Long UserId = user.id();
        return bookingRepository.findByUserId(UserId).orElseThrow(() -> new RuntimeException("No Bookings Found"));
    }

}

