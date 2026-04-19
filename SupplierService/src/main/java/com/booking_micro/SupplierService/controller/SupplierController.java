package com.booking_micro.SupplierService.controller;

import com.booking_micro.SupplierService.dto.AcceptResponseDto;
import com.booking_micro.SupplierService.model.BookingRequestEntity;
import com.booking_micro.SupplierService.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/see-requests")
    public ResponseEntity<List<BookingRequestEntity>> seeRequests() {
        return ResponseEntity.ok(supplierService.getPendingRequests());
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<AcceptResponseDto> acceptRequest(@PathVariable Long id, @RequestHeader(value="X-User-Id", defaultValue="unknown") String providerId) {
        try {
            AcceptResponseDto response = supplierService.acceptPendingRequest(id, providerId);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new AcceptResponseDto(id, providerId, "Request is not PENDING"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AcceptResponseDto(id, providerId, "Request not found"));
        }
    }
}
