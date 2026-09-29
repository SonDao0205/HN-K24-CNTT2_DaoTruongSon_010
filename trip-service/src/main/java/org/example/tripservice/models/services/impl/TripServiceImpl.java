package org.example.tripservice.models.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.tripservice.exceptions.TripNotFoundException;
import org.example.tripservice.models.entities.Trip;
import org.example.tripservice.models.repositories.TripRepository;
import org.example.tripservice.models.services.TripService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;

    @Override
    @Cacheable(cacheNames = "trips", key = "#id", sync = true)
    @Transactional(readOnly = true)
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new TripNotFoundException(id));
    }
}
