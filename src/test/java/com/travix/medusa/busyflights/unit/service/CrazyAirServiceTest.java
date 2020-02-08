package com.travix.medusa.busyflights.unit.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.CrazyAirService;
import com.travix.medusa.busyflights.service.FlightSupplier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CrazyAirServiceTest {

    private CrazyAirService crazyAirService = new CrazyAirService();

    @Test
    public void mapToCrazyAirRequest() {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("2020-02-18T01:00:00+01:00");
        busyFlightsRequest.setReturnDate("2020-02-19T01:00:00+01:00");
        busyFlightsRequest.setNumberOfPassengers(4);
        busyFlightsRequest.setOrigin("GIG");
        busyFlightsRequest.setDestination("LCY");

        CrazyAirRequest crazyAirRequest = crazyAirService.map(busyFlightsRequest);

        assertEquals(crazyAirRequest.getDepartureDate(), "2020-02-18T01:00:00+01:00");
        assertEquals(crazyAirRequest.getReturnDate(), "2020-02-19T01:00:00+01:00");
        assertEquals(crazyAirRequest.getPassengerCount(), 4);
        assertEquals(crazyAirRequest.getOrigin(), "GIG");
        assertEquals(crazyAirRequest.getDestination(), "LCY");
    }

    @Test
    public void mapToBusyFlightResponse() {
        CrazyAirResponse crazyAirResponse = new CrazyAirResponse();
        crazyAirResponse.setAirline("LATAM");
        crazyAirResponse.setArrivalDate("2020-02-18T01:00:00+01:00");
        crazyAirResponse.setDepartureDate("2020-02-19T01:00:00+01:00");
        crazyAirResponse.setCabinclass("B");
        crazyAirResponse.setDepartureAirportCode("GIG");
        crazyAirResponse.setDestinationAirportCode("LCY");
        crazyAirResponse.setPrice(120);

        BusyFlightsResponse response = crazyAirService.map(crazyAirResponse);
        assertEquals(response.getArrivalDate(), "2020-02-18T01:00:00+01:00");
        assertEquals(response.getDepartureDate(), "2020-02-19T01:00:00+01:00");
        assertEquals(response.getFare().intValue(), 120);
        assertEquals(response.getDepartureAirportCode(), "GIG");
        assertEquals(response.getDestinationAirportCode(), "LCY");
        assertEquals(response.getSupplier(), SuplierEnum.CrazyAir.name());
    }

}
