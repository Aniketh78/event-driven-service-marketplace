package com.booking_micro.bookingService.entity;

import com.booking_micro.bookingService.constants.BookingEntityCons;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookingEntityCons status;

    private Long providerId;

}
