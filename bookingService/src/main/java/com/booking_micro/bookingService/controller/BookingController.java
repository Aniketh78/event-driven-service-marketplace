package com.booking_micro.bookingService.controller;

import com.booking_micro.bookingService.dto.BookingRequestDto;
import com.booking_micro.bookingService.dto.ResponseDto;
import com.booking_micro.bookingService.entity.Booking;
import com.booking_micro.bookingService.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<String>> createBooking(@RequestBody BookingRequestDto bookingRequestDto){
        String result = bookingService.CreateBooking(bookingRequestDto);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .body(result)
                .build());
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<ResponseDto<List<Booking>>> getMyBookings(){
        List<Booking> bookings = bookingService.getMyBooking();
        return ResponseEntity.ok(ResponseDto.<List<Booking>>builder()
                .statusCode(HttpStatus.OK.value())
                .body(bookings)
                .build());
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<ResponseDto<String>> cancelBooking(@PathVariable Long id){
        String result = bookingService.cancelBooking(id);
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .body(result)
                .build());
    }
}