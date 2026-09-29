package org.example.ticketservice.models.dto.responses;

public record TicketDetailResponse(
        Long id,
        Long tripId,
        Integer seats,
        Double ticketPrice,
        Double lineTotal
) {
}
