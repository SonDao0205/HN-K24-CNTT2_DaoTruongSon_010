package org.example.ticketservice.models.dto.responses;

public record TripResponse(
        Long id,
        String departure,
        String destination,
        Double ticketPrice,
        Integer availableSeats
) {
}
