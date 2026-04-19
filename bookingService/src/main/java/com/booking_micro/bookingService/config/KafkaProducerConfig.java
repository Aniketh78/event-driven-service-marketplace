package com.booking_micro.bookingService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.apache.kafka.common.serialization.StringSerializer;
import com.booking_micro.bookingService.entity.Booking;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public DefaultKafkaProducerFactory<String, Booking> kafkaProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", "localhost:19092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", org.springframework.kafka.support.serializer.JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Booking> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }



}
