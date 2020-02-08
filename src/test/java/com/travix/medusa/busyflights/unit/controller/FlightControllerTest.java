package com.travix.medusa.busyflights.unit.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.controller.FlightController;
import com.travix.medusa.busyflights.domain.SuplierEnum;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.FlightService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightController.class)
public class FlightControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FlightService flightService;

    @Test
    public void searchFlightsWithoutRequiredParams() throws Exception {
        mvc.perform(
                get("/flights")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void searchFlightsWithRequiredParams() throws Exception {

        BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
        busyFlightsResponse.setSupplier(SuplierEnum.CrazyAir.name());
        busyFlightsResponse.setFare(BigDecimal.valueOf(500));
        busyFlightsResponse.setAirline("LATAM");

        BusyFlightsResponse busyFlightsResponse2 = new BusyFlightsResponse();
        busyFlightsResponse2.setSupplier(SuplierEnum.ToughJet.name());
        busyFlightsResponse2.setFare(BigDecimal.valueOf(500));
        busyFlightsResponse2.setAirline("GOL");

        List<BusyFlightsResponse> busyFlightsResponseList = Arrays.asList(busyFlightsResponse, busyFlightsResponse2);


        Mockito.when(flightService.search(Mockito.any(BusyFlightsRequest.class))).thenReturn(Arrays.asList(busyFlightsResponse, busyFlightsResponse2));

        mvc.perform(
                get("/flights")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("origin", "GIG")
                        .param("destination", "LOL")
                        .param("departureDate", "2020-02-18T01:00:00+01:00")
                        .param("returnDate", "2020-02-18T01:00:00+01:00")
                        .param("numberOfPassengers", "4"))
                .andExpect(status().isOk())
                .andDo(resHandler -> {
                    String resJson = resHandler.getResponse().getContentAsString();
                    BusyFlightsResponse[] res = mapper.readValue(resJson, BusyFlightsResponse[].class);
                    assertEquals(busyFlightsResponseList.size(), res.length);
                });
    }
}
