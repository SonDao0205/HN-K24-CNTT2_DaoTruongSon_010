package org.example.ticketservice.exceptions;

public class TripServiceException extends RuntimeException {
    public TripServiceException(String message) {
        super(message);
    }

    public TripServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
