package org.example.ticketservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.ticketservice.exceptions.TripServiceException;
import org.example.ticketservice.models.constants.TicketStatus;
import org.example.ticketservice.models.dto.requests.CreateTicketRequest;
import org.example.ticketservice.models.dto.responses.TicketDetailResponse;
import org.example.ticketservice.models.dto.responses.TicketResponse;
import org.example.ticketservice.models.dto.responses.TripResponse;
import org.example.ticketservice.models.entities.Ticket;
import org.example.ticketservice.models.entities.TicketDetail;
import org.example.ticketservice.models.repositories.TicketDetailRepository;
import org.example.ticketservice.models.repositories.TicketRepository;
import org.example.ticketservice.models.services.TicketService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketDetailRepository ticketDetailRepository;
    private final TripGatewayService tripGatewayService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TICKET_CREATED_TOPIC = "ticket-created";

    @Override
    @Transactional
    public TicketResponse createTicket(CreateTicketRequest request) {
        List<TripResponse> trips = new ArrayList<>();
        double totalAmount = 0;

        for (var item : request.items()) {
            TripResponse trip = tripGatewayService.getTripById(item.tripId());

            if (item.seat() <= 0) {
                throw new TripServiceException("Seats must be greater than 0");
            }
            if (item.seat() > trip.availableSeats()) {
                throw new TripServiceException("Trip " + item.tripId() + " does not have enough available seats");
            }

            trips.add(trip);
            totalAmount += trip.ticketPrice() * item.seat();
        }

        Ticket ticket = ticketRepository.save(Ticket.builder()
                .passengerName(request.passengerName().trim())
                .passengerEmail(request.passengerEmail().trim())
                .totalAmount(totalAmount)
                .status(TicketStatus.PENDING)
                .build());

        List<TicketDetail> details = new ArrayList<>();
        for (int index = 0; index < request.items().size(); index++) {
            var item = request.items().get(index);
            TripResponse trip = trips.get(index);
            double lineTotal = trip.ticketPrice() * item.seat();

            details.add(TicketDetail.builder()
                        .tripId(trip.id())
                        .seats(item.seat())
                        .ticketPrice(trip.ticketPrice())
                        .lineTotal(lineTotal)
                        .ticket(ticket)
                        .build());
        }

        List<TicketDetail> savedDetails = ticketDetailRepository.saveAll(details);
        kafkaTemplate.send(TICKET_CREATED_TOPIC, ticket.getPassengerEmail());

        return toResponse(ticket, savedDetails);
    }

    private TicketResponse toResponse(Ticket ticket, List<TicketDetail> savedDetails) {
        List<TicketDetailResponse> detailResponses = new ArrayList<>(savedDetails.size());
        for (TicketDetail detail : savedDetails) {
            detailResponses.add(new TicketDetailResponse(
                    detail.getId(),
                    detail.getTripId(),
                    detail.getSeats(),
                    detail.getTicketPrice(),
                    detail.getLineTotal()
            ));
        }

        return new TicketResponse(
                ticket.getId(),
                ticket.getPassengerName(),
                ticket.getPassengerEmail(),
                ticket.getTotalAmount(),
                ticket.getStatus(),
                detailResponses
        );
    }

}
