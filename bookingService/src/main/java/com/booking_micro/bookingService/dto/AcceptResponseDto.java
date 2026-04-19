package com.booking_micro.bookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptResponseDto {
    private Long requestId;
    private String providerId;
    private String message;
}
