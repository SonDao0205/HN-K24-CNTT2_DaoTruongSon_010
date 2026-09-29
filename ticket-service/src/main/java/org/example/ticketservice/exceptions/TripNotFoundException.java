package org.example.ticketservice.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long id) {
        super("Trip not found with ID: " + id);
    }
}
