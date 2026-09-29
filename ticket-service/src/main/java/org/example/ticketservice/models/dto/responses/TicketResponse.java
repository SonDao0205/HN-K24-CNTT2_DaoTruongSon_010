package org.example.ticketservice.models.dto.responses;

import org.example.ticketservice.models.constants.TicketStatus;

import java.util.List;

public record TicketResponse(
        Long id,
        String passengerName,
        String passengerEmail,
        Double totalAmount,
        TicketStatus status,
        List<TicketDetailResponse> details
) {
}
