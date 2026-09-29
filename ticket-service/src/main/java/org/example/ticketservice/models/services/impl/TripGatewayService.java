package org.example.ticketservice.models.services.impl;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.example.ticketservice.clients.TripClient;
import org.example.ticketservice.exceptions.TripNotFoundException;
import org.example.ticketservice.exceptions.TripServiceException;
import org.example.ticketservice.models.dto.responses.TripResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripGatewayService {
    private final TripClient tripClient;

    @CircuitBreaker(name = "tripService", fallbackMethod = "getTripByIdFallback")
    public TripResponse getTripById(Long tripId) {
        TripResponse trip = tripClient.getTripById(tripId);

        if (trip == null || trip.id() == null || trip.ticketPrice() == null || trip.availableSeats() == null) {
            throw new TripServiceException("Trip service returned invalid data for trip " + tripId);
        }

        return trip;
    }

    private TripResponse getTripByIdFallback(Long tripId, Throwable throwable) {
        if (throwable instanceof FeignException.NotFound) {
            throw new TripNotFoundException(tripId);
        }
        if (throwable instanceof TripNotFoundException tripNotFoundException) {
            throw tripNotFoundException;
        }
        if (throwable instanceof TripServiceException tripServiceException) {
            throw tripServiceException;
        }

        throw new TripServiceException("Trip service is unavailable for trip " + tripId, throwable);
    }
}
