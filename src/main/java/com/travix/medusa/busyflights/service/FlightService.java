package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final List<FlightSupplier> flightSuppliers;
    private static Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    public FlightService(List<FlightSupplier> flightSuppliers) {
        this.flightSuppliers = flightSuppliers;
    }

    @SuppressWarnings("unchecked")
    public List<BusyFlightsResponse> search(BusyFlightsRequest request) {
        return (List<BusyFlightsResponse>) flightSuppliers.stream().map(flightSupplier -> {
            try {
                return flightSupplier.search(request);
            } catch (Exception e) {
                logger.error("There is an error in processing request for a supplier", e);
                return Collections.emptyList();
            }
        }).flatMap(arr -> arr.stream())
                .sorted(Comparator.comparing(BusyFlightsResponse::getFare))
                .collect(Collectors.toList());
    }
}
