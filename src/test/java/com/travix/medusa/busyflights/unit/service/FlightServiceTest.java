package com.travix.medusa.busyflights.unit.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.CrazyAirService;
import com.travix.medusa.busyflights.service.FlightService;
import com.travix.medusa.busyflights.service.FlightSupplier;
import com.travix.medusa.busyflights.service.ToughJetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @Mock
    private CrazyAirService crazyAirService;


    @Mock
    private ToughJetService toughJetService;

    @Mock
    private List<FlightSupplier> flightSuppliers;

    @InjectMocks
    FlightService flightService;

    @Test
    public void search() {

        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("2020-02-18T01:00:00+01:00");
        busyFlightsRequest.setReturnDate("2020-02-19T01:00:00+01:00");
        busyFlightsRequest.setNumberOfPassengers(4);
        busyFlightsRequest.setOrigin("GIG");
        busyFlightsRequest.setDestination("LCY");


        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setSupplier(SuplierEnum.CrazyAir.name());
        busyFlightsResponse.setFare(BigDecimal.valueOf(500));
        busyFlightsResponse.setAirline("LATAM");

        BusyFlightsResponse busyFlightsResponse2 = new BusyFlightsResponse();
        busyFlightsResponse2.setSupplier(SuplierEnum.ToughJet.name());
        busyFlightsResponse2.setFare(BigDecimal.valueOf(500));
        busyFlightsResponse2.setAirline("GOL");

        BusyFlightsResponse busyFlightsResponse3 = new BusyFlightsResponse();
        busyFlightsResponse3.setSupplier(SuplierEnum.ToughJet.name());
        busyFlightsResponse3.setFare(BigDecimal.valueOf(500));
        busyFlightsResponse3.setAirline("GOL");

        when(flightSuppliers.stream()).thenReturn(Stream.of(crazyAirService, toughJetService));

        Mockito.when(crazyAirService.search(Mockito.any(BusyFlightsRequest.class))).thenReturn(Collections.singletonList(busyFlightsResponse));

        Mockito.when(toughJetService.search(Mockito.any(BusyFlightsRequest.class))).thenReturn(Arrays.asList(busyFlightsResponse2, busyFlightsResponse3));

        List<BusyFlightsResponse> busyFlightsResponses = flightService.search(busyFlightsRequest);

        assertEquals(busyFlightsResponses.stream().filter(r->r.getSupplier().equals(SuplierEnum.CrazyAir.name())).count(), 1);

        assertEquals(busyFlightsResponses.stream().filter(r->r.getSupplier().equals(SuplierEnum.ToughJet.name())).count(), 2);
    }
}
