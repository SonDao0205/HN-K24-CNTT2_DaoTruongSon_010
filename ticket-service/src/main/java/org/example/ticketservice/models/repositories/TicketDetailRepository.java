package org.example.ticketservice.models.repositories;

import org.example.ticketservice.models.entities.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDetailRepository extends JpaRepository<TicketDetail, Long> {
}
