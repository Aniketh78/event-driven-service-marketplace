package com.booking_micro.bookingService.config;

public record UserPrincipal(
        Long id,
        String email,
        String roles
) {}