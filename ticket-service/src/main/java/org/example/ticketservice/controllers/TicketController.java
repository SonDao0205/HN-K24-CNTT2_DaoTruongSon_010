package org.example.ticketservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ticketservice.models.dto.requests.CreateTicketRequest;
import org.example.ticketservice.models.dto.responses.TicketResponse;
import org.example.ticketservice.models.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(request));
    }
}
