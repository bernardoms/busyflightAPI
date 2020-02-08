package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ToughJetService implements FlightSupplier<ToughJetRequest, ToughJetResponse> {

    @Override
    public Optional<List<ToughJetResponse>> supplierSearch(ToughJetRequest supplierRequest) {
        return Optional.of(Collections.singletonList(new ToughJetResponse()));
    }

    @Override
    public ToughJetRequest map(BusyFlightsRequest flightsRequest) {

        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom(flightsRequest.getOrigin());
        toughJetRequest.setTo(flightsRequest.getDestination());
        toughJetRequest.setInboundDate(flightsRequest.getDepartureDate());
        toughJetRequest.setOutboundDate(flightsRequest.getReturnDate());
        toughJetRequest.setNumberOfAdults(flightsRequest.getNumberOfPassengers());

        return toughJetRequest;
    }

    @Override
    public BusyFlightsResponse map(ToughJetResponse supplierResponse) {

        BigDecimal fare = new BigDecimal(supplierResponse.getBasePrice()).add(new BigDecimal(supplierResponse.getTax()));
        fare = fare.subtract(fare.multiply(new BigDecimal(supplierResponse.getDiscount())).divide(new BigDecimal(100), new MathContext(2, RoundingMode.HALF_UP)));


        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setAirline(supplierResponse.getCarrier());
        busyFlightsResponse.setArrivalDate(supplierResponse.getInboundDateTime());
        busyFlightsResponse.setDepartureDate(supplierResponse.getOutboundDateTime());
        busyFlightsResponse.setFare(fare);
        busyFlightsResponse.setDepartureAirportCode(supplierResponse.getDepartureAirportName());
        busyFlightsResponse.setDestinationAirportCode(supplierResponse.getArrivalAirportName());
        busyFlightsResponse.setSupplier(SuplierEnum.ToughJet.name());

        return busyFlightsResponse;
    }
}
