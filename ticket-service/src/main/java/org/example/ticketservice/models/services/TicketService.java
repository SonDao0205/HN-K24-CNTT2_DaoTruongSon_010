package org.example.ticketservice.models.services;

import org.example.ticketservice.models.dto.requests.CreateTicketRequest;
import org.example.ticketservice.models.dto.responses.TicketResponse;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest request);

}
