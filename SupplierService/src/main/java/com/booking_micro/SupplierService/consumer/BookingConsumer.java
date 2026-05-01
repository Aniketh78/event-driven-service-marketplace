package com.booking_micro.SupplierService.consumer;

import com.booking_micro.SupplierService.dto.BookingDto;
import com.booking_micro.SupplierService.model.BookingRequestEntity;
import com.booking_micro.SupplierService.repository.BookingRequestRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingConsumer {

    private final BookingRequestRepository repository;

    public BookingConsumer(BookingRequestRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "booking-topic", groupId = "supplier-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(BookingDto bookingDto) {
        Optional<BookingRequestEntity> existingBooking = repository.findByBookingId(bookingDto.getId());

        if (existingBooking.isPresent()) {
            BookingRequestEntity entity = existingBooking.get();
            entity.setStatus(bookingDto.getStatus().toString());
            repository.save(entity);
        } else {
            BookingRequestEntity entity = new BookingRequestEntity();
            entity.setBookingId(bookingDto.getId());
            entity.setType(bookingDto.getType());
            entity.setStatus(bookingDto.getStatus() != null ? bookingDto.getStatus().toString() : "PENDING");
            entity.setUserId(String.valueOf(bookingDto.getUserId()));
            repository.save(entity);
        }
    }
}