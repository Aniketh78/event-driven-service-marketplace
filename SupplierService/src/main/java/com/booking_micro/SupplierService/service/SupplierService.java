package com.booking_micro.SupplierService.service;

import com.booking_micro.SupplierService.dto.AcceptResponseDto;
import com.booking_micro.SupplierService.model.BookingRequestEntity;
import com.booking_micro.SupplierService.repository.BookingRequestRepository;
import com.booking_micro.SupplierService.producer.SupplierProducer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final BookingRequestRepository repository;
    private final SupplierProducer producer;

    public SupplierService(BookingRequestRepository repository, SupplierProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public List<BookingRequestEntity> getPendingRequests() {
        return repository.findByStatus("PENDING");
    }

    public AcceptResponseDto acceptPendingRequest(Long id, String providerId) {
        BookingRequestEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!"PENDING".equals(entity.getStatus())) {
            throw new IllegalStateException("Request is not PENDING");
        }

        entity.setStatus("ACCEPTED");
        entity.setProviderId(providerId);
        repository.save(entity);

        AcceptResponseDto response = new AcceptResponseDto(entity.getBookingId(), providerId, "ACCEPTED");
        producer.sendBookingAccepted(response);

        return response;
    }
}
