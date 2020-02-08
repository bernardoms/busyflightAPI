package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CrazyAirService implements FlightSupplier <CrazyAirRequest, CrazyAirResponse> {

    @Override
    public Optional<List<CrazyAirResponse>> supplierSearch(CrazyAirRequest supplierRequest) {
        return Optional.of(Collections.singletonList(new CrazyAirResponse()));
    }

    @Override
    public CrazyAirRequest map(BusyFlightsRequest flightsRequest) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();

        crazyAirRequest.setDepartureDate(flightsRequest.getDepartureDate());
        crazyAirRequest.setReturnDate(flightsRequest.getReturnDate());
        crazyAirRequest.setDestination(flightsRequest.getDestination());
        crazyAirRequest.setOrigin(flightsRequest.getDestination());
        crazyAirRequest.setPassengerCount(flightsRequest.getNumberOfPassengers());

        return crazyAirRequest;
    }

    @Override
    public BusyFlightsResponse map(CrazyAirResponse supplierResponse) {

        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setDestinationAirportCode(supplierResponse.getDestinationAirportCode());
        busyFlightsResponse.setDepartureAirportCode(supplierResponse.getDepartureAirportCode());
        busyFlightsResponse.setAirline(supplierResponse.getAirline());
        busyFlightsResponse.setDepartureDate(supplierResponse.getDepartureDate());
        busyFlightsResponse.setArrivalDate(supplierResponse.getArrivalDate());
        busyFlightsResponse.setFare(BigDecimal.valueOf(supplierResponse.getPrice()));
        busyFlightsResponse.setSupplier(SuplierEnum.CrazyAir.name());

        return busyFlightsResponse;
    }
}
