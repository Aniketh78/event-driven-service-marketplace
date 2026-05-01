package com.booking_micro.SupplierService.consumer;

import com.booking_micro.SupplierService.dto.BookingDto;
import com.booking_micro.SupplierService.model.BookingRequestEntity;
import com.booking_micro.SupplierService.repository.BookingRequestRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingConsumer {

    private final BookingRequestRepository repository;

    public BookingConsumer(BookingRequestRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "booking-topic", groupId = "supplier-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(BookingDto bookingDto) {
        BookingRequestEntity entity = new BookingRequestEntity();
        entity.setBookingId(bookingDto.getId());
        entity.setType(bookingDto.getType());
        entity.setStatus("PENDING");
        entity.setUserId(String.valueOf(bookingDto.getUserId()));
        repository.save(entity);
    }
}
