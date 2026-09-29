package org.example.ticketservice.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trip_id")
    private Long tripId;

    @Column(name = "seats")
    private Integer seats;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @Column(name = "line_total")
    private Double lineTotal;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}