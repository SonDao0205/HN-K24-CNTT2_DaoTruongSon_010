package org.example.tripservice.exceptions;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long id) {
        super("Không tìm thấy chuyến đi với id: " + id);
    }
}
