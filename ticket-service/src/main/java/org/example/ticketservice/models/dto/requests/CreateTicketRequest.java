package org.example.ticketservice.models.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateTicketRequest(
        @NotBlank(message = "Passenger name is required")
        @Size(max = 255, message = "Passenger name must not exceed 255 characters")
        String passengerName,

        @NotBlank(message = "Passenger email is required")
        @Size(max = 255, message = "Passenger email must not exceed 255 characters")
        String passengerEmail,

        @NotEmpty(message = "Ticket must contain at least one item")
        List<@Valid CreateTicketDetailRequest> items
) {
}
