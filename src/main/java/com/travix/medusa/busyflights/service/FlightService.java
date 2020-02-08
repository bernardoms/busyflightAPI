package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final List<FlightSupplier> flightSuppliers;

    @Autowired
    public FlightService(List<FlightSupplier> flightSuppliers){
        this.flightSuppliers = flightSuppliers;
    }

    @SuppressWarnings("unchecked")
    public List<BusyFlightsResponse> search(BusyFlightsRequest request) {
        return (List<BusyFlightsResponse>) flightSuppliers.stream().map(flightSupplier -> flightSupplier.search(request)).flatMap(arr -> arr.stream()).sorted(Comparator.comparing(BusyFlightsResponse::getFare)).collect(Collectors.toList());
    }
}
