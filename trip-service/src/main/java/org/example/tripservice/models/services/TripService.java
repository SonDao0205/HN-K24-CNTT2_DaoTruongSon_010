package org.example.tripservice.models.services;

import org.example.tripservice.models.entities.Trip;

public interface TripService {
    Trip getTripById(Long id);
}
