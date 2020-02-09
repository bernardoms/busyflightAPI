package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class CrazyAirService implements FlightSupplier <CrazyAirRequest, CrazyAirResponse> {

    @Value("${crazyair.server}")
    private String crazyAirServer;

    private RestTemplate restTemplate;

    @Autowired
    public CrazyAirService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CrazyAirResponse> supplierSearch(CrazyAirRequest supplierRequest) {
        return Arrays.asList(restTemplate.getForObject(buildUri(supplierRequest), CrazyAirResponse[].class));
    }

    @Override
    public CrazyAirRequest map(BusyFlightsRequest flightsRequest) {
        CrazyAirRequest crazyAirRequest = new CrazyAirRequest();

        crazyAirRequest.setDepartureDate(flightsRequest.getDepartureDate());
        crazyAirRequest.setReturnDate(flightsRequest.getReturnDate());
        crazyAirRequest.setDestination(flightsRequest.getDestination());
        crazyAirRequest.setOrigin(flightsRequest.getOrigin());
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

    private String buildUri(CrazyAirRequest crazyAirRequest){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(crazyAirServer);
        builder.queryParam("origin",crazyAirRequest.getOrigin());
        builder.queryParam("destination",crazyAirRequest.getDestination());
        builder.queryParam("departureDate",crazyAirRequest.getDepartureDate());
        builder.queryParam("returnDate",crazyAirRequest.getReturnDate());
        builder.queryParam("passengerCount",crazyAirRequest.getPassengerCount());
        return builder.toUriString();
    }
}
