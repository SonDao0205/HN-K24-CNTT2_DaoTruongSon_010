package org.example.ticketservice.clients;

import org.example.ticketservice.models.dto.responses.TripResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trip-service", path = "/api/v1/trips")
public interface TripClient {

    @GetMapping("/{id}")
    TripResponse getTripById(@PathVariable Long id);

}
