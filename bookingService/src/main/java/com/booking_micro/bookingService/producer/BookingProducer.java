package com.booking_micro.bookingService.producer;

import com.booking_micro.bookingService.entity.Booking;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingProducer {

    private final KafkaTemplate<String, Booking> kafkaTemplate;

    public BookingProducer(KafkaTemplate<String, Booking> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    private final String topic = "booking-topic";

    public void sendBooking(Booking booking) {
        kafkaTemplate.send(topic, booking);
    }
}
