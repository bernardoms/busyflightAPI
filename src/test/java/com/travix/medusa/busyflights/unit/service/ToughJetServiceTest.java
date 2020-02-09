package com.travix.medusa.busyflights.unit.service;

import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.ToughJetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ToughJetServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private ToughJetService toughJetService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String toughJetServer = "http://localhost/toughJet/flights";
        toughJetService = new ToughJetService(restTemplate, toughJetServer);
    }

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
        ToughJetResponse toughJetResponse = generateToughJetResponse();

        BusyFlightsResponse response = toughJetService.map(toughJetResponse);

        assertEquals(response.getArrivalDate(), "2020-02-18T01:00:00+01:00");
        assertEquals(response.getDepartureDate(), "2020-02-19T01:00:00+01:00");
        assertEquals(response.getFare().intValue(), 463);
        assertEquals(response.getDepartureAirportCode(), "GIG");
        assertEquals(response.getDestinationAirportCode(), "LCY");
        assertEquals(response.getSupplier(), SuplierEnum.ToughJet.name());
    }

    @Test
    public void mountURLForSupplier() {
        ToughJetRequest toughJetRequest = generateToughJetRequest();

        assertEquals(toughJetService.buildUri(toughJetRequest), "http://localhost/toughJet/flights?from=GIG&to=LCY&outboundDate=2020-02-19T01:00:00%2B01:00&inboundDate=2020-02-19T01:00:00%2B01:00&numberOfAdults=4");
    }

    @Test
    public void searchSupplier() {

        ToughJetResponse[] toughJetResponses = {generateToughJetResponse()};

        when(restTemplate.getForObject(anyString(), eq(ToughJetResponse[].class))).thenReturn(toughJetResponses);

        toughJetService.supplierSearch(generateToughJetRequest());
    }

    public ToughJetRequest generateToughJetRequest() {
        ToughJetRequest toughJetRequest = new ToughJetRequest();
        toughJetRequest.setFrom("GIG");
        toughJetRequest.setOutboundDate("2020-02-19T01:00:00+01:00");
        toughJetRequest.setInboundDate("2020-02-19T01:00:00+01:00");
        toughJetRequest.setTo("LCY");
        toughJetRequest.setNumberOfAdults(4);

        return toughJetRequest;
    }

    public ToughJetResponse generateToughJetResponse() {
        ToughJetResponse toughJetResponse = new ToughJetResponse();
        toughJetResponse.setCarrier("LATAM");
        toughJetResponse.setInboundDateTime("2020-02-18T01:00:00+01:00");
        toughJetResponse.setOutboundDateTime("2020-02-19T01:00:00+01:00");
        toughJetResponse.setDepartureAirportName("GIG");
        toughJetResponse.setArrivalAirportName("LCY");
        toughJetResponse.setBasePrice(500);
        toughJetResponse.setDiscount(10);
        toughJetResponse.setTax(15);

        return toughJetResponse;
    }
}
