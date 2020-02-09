package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface FlightSupplier <SREQ, SRESP> {
    List<SRESP> supplierSearch(SREQ supplierRequest);

    SREQ map(BusyFlightsRequest flightsRequest);

    BusyFlightsResponse map(SRESP supplierResponse);

    String buildUri(SREQ supplierRequest);

    default List<BusyFlightsResponse> search(BusyFlightsRequest flightsRequest) {
        return this.supplierSearch(this.map(flightsRequest)).stream().map(this::map).collect(toList());
    }
}
