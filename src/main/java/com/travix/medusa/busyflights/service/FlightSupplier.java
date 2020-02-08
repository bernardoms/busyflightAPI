package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface FlightSupplier <SREQ, SRESP> {
    Optional<List<SRESP>> supplierSearch(SREQ supplierRequest);

    SREQ map(BusyFlightsRequest flightsRequest);

    BusyFlightsResponse map(SRESP supplierResponse);

    default List<BusyFlightsResponse> search(BusyFlightsRequest flightsRequest) {
        Optional<List<SRESP>> suppliersResponse = this.supplierSearch(this.map(flightsRequest));
        return suppliersResponse.map(resp -> resp.stream().map(this::map).collect(Collectors.toList())).orElse(Collections.emptyList());
    }
}
