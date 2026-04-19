package com.booking_micro.bookingService.consumer;

import com.booking_micro.bookingService.dto.AcceptResponseDto;
import com.booking_micro.bookingService.entity.Booking;
import com.booking_micro.bookingService.constants.BookingEntityCons;
import com.booking_micro.bookingService.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingAcceptedConsumer {

    private final BookingRepository repository;

    public BookingAcceptedConsumer(BookingRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "booking-accepted-topic", groupId = "booking-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(AcceptResponseDto response) {

        repository.findById(response.getRequestId()).ifPresent(booking -> {
            booking.setStatus(BookingEntityCons.ACCEPTED);
            booking.setProviderId(Long.valueOf(response.getProviderId()));
            repository.save(booking);
            log.info("Updated booking {} to ACCEPTED with provider {}", booking.getId(), booking.getProviderId());
        });
    }
}
