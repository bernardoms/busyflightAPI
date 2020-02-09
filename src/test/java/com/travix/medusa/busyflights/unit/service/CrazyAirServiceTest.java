package com.travix.medusa.busyflights.unit.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.CrazyAirService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrazyAirServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private CrazyAirService crazyAirService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String crazyAirServer = "http://localhost/crazyAir/flights";
        crazyAirService = new CrazyAirService(restTemplate, crazyAirServer);
    }

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
        CrazyAirResponse crazyAirResponse = generateCrazyAirResponse();

        BusyFlightsResponse response = crazyAirService.map(crazyAirResponse);
        assertEquals(response.getArrivalDate(), "2020-02-18T01:00:00+01:00");
        assertEquals(response.getDepartureDate(), "2020-02-19T01:00:00+01:00");
        assertEquals(response.getFare().intValue(), 120);
        assertEquals(response.getDepartureAirportCode(), "GIG");
        assertEquals(response.getDestinationAirportCode(), "LCY");
        assertEquals(response.getSupplier(), SuplierEnum.CrazyAir.name());
    }

    @Test
    public void mountURLForSupplier() {
        assertEquals(crazyAirService.buildUri(generateCrazyAirRequest()), "http://localhost/crazyAir/flights?origin=GIG&destination=LCY&departureDate&returnDate=2020-02-19T01:00:00%2B01:00&passengerCount=4");
    }

    @Test
    public void searchSupplier() {
        CrazyAirResponse[] crazyAirResponses = {generateCrazyAirResponse()};

        when(restTemplate.getForObject(anyString(), eq(CrazyAirResponse[].class))).thenReturn(crazyAirResponses);

        crazyAirService.supplierSearch(generateCrazyAirRequest());
    }

    public CrazyAirRequest generateCrazyAirRequest() {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
        crazyAirRequest.setOrigin("GIG");
        crazyAirRequest.setReturnDate("2020-02-19T01:00:00+01:00");
        crazyAirRequest.setDestination("2020-02-19T01:00:00+01:00");
        crazyAirRequest.setDestination("LCY");
        crazyAirRequest.setPassengerCount(4);

        return crazyAirRequest;
    }

    public CrazyAirResponse generateCrazyAirResponse() {
        CrazyAirResponse crazyAirResponse = new CrazyAirResponse();
        crazyAirResponse.setAirline("LATAM");
        crazyAirResponse.setArrivalDate("2020-02-18T01:00:00+01:00");
        crazyAirResponse.setDepartureDate("2020-02-19T01:00:00+01:00");
        crazyAirResponse.setCabinclass("B");
        crazyAirResponse.setDepartureAirportCode("GIG");
        crazyAirResponse.setDestinationAirportCode("LCY");
        crazyAirResponse.setPrice(120);

        return crazyAirResponse;
    }

}
