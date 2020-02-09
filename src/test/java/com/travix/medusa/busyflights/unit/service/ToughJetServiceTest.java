package com.travix.medusa.busyflights.unit.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.ToughJetService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ToughJetServiceTest {

    @InjectMocks
    private ToughJetService toughJetService;

    @Test
    public void mapToCrazyAirRequest() {
        BusyFlightsRequest busyFlightsRequest = new BusyFlightsRequest();
        busyFlightsRequest.setDepartureDate("2020-02-18T01:00:00+01:00");
        busyFlightsRequest.setReturnDate("2020-02-19T01:00:00+01:00");
        busyFlightsRequest.setNumberOfPassengers(4);
        busyFlightsRequest.setOrigin("GIG");
        busyFlightsRequest.setDestination("LCY");

        ToughJetRequest toughJetRequest = toughJetService.map(busyFlightsRequest);

        assertEquals(toughJetRequest.getOutboundDate(), "2020-02-19T01:00:00+01:00");
        assertEquals(toughJetRequest.getInboundDate(), "2020-02-18T01:00:00+01:00");
        assertEquals(toughJetRequest.getNumberOfAdults(), 4);
        assertEquals(toughJetRequest.getFrom(), "GIG");
        assertEquals(toughJetRequest.getTo(), "LCY");
    }

    @Test
    public void mapToBusyFlightResponse() {
        ToughJetResponse toughJetResponse = new ToughJetResponse();
        toughJetResponse.setCarrier("LATAM");
        toughJetResponse.setInboundDateTime("2020-02-18T01:00:00+01:00");
        toughJetResponse.setOutboundDateTime("2020-02-19T01:00:00+01:00");
        toughJetResponse.setDepartureAirportName("GIG");
        toughJetResponse.setArrivalAirportName("LCY");
        toughJetResponse.setBasePrice(500);
        toughJetResponse.setDiscount(10);
        toughJetResponse.setTax(15);

        BusyFlightsResponse response = toughJetService.map(toughJetResponse);

        assertEquals(response.getArrivalDate(), "2020-02-18T01:00:00+01:00");
        assertEquals(response.getDepartureDate(), "2020-02-19T01:00:00+01:00");
        assertEquals(response.getFare().intValue(), 463);
        assertEquals(response.getDepartureAirportCode(), "GIG");
        assertEquals(response.getDestinationAirportCode(), "LCY");
        assertEquals(response.getSupplier(), SuplierEnum.ToughJet.name());
    }

}
