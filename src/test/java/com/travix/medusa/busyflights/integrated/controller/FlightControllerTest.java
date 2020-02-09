package com.travix.medusa.busyflights.integrated.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void searchFlightsWithRequiredParams() throws Exception {
        mockMvc.perform(
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
                    assertEquals(4, res.length);
                });
    }
}
