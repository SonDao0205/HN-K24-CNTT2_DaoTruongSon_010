package org.example.ticketservice.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.ticketservice.models.constants.TicketStatus;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "passenger_email")
    private String passengerEmail;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}