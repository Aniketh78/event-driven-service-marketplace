package com.booking_micro.SupplierService.producer;

import com.booking_micro.SupplierService.dto.AcceptResponseDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SupplierProducer {

    private final KafkaTemplate<String, AcceptResponseDto> kafkaTemplate;

    public SupplierProducer(KafkaTemplate<String, AcceptResponseDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingAccepted(AcceptResponseDto response) {
        kafkaTemplate.send("booking-accepted-topic", response);
    }
}
