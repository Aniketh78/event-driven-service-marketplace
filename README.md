<div align="center">

# Event-Driven Service Marketplace

**A production-grade microservices platform for service booking and supplier management, built with event-driven architecture using Spring Boot, Spring Cloud, and Apache Kafka.**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Latest-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)](https://kafka.apache.org/)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

---

</div>

## Overview

This platform implements a fully distributed microservices architecture designed around service booking and supplier fulfillment.

## Features

- **Service Booking**: Users can create service requests.
- **Booking Cancellation**: Users can cancel existing bookings, which triggers an update to the supplier via Kafka.
- **Supplier Fulfillment**: Suppliers receive and process booking requests in real-time.

## Architecture

### Actor Use Cases

```mermaid
graph LR
    User([User])
    Supplier([Supplier])

    subgraph "Client Suite"
        User -->|Creates| CreateBooking(Place a Booking)
        User -->|Cancels| CancelBooking(Cancel a Booking)
        User -->|Reads| ViewBookings(View Own Bookings)
    end

    subgraph "Supplier Suite"
        Supplier -->|Reads| ViewRequests(View Pending Requests)
        Supplier -->|Updates| SyncStatus(Sync Status via Kafka)
    end
```

## API Reference

### Booking Service
| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/api/bookings/create` | `POST` | Create a new booking request |
| `/api/bookings/my-bookings` | `GET` | Retrieve logged-in user's bookings |
| `/api/bookings/cancel/{id}` | `POST` | Cancel an existing booking |

## Event Flow (Cancellation)
1. **User** sends a cancel request to **Booking Service**.
2. **Booking Service** updates DB to `CANCELLED` status.
3. **Booking Service** produces a message to `booking-topic`.
4. **Supplier Service** consumes the message and updates its local record for that `bookingId`.