package com.travix.medusa.busyflights.repository;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;

import java.util.List;
import java.util.Optional;

public class CrazyAirRepositoryImpl implements CrazyAirRepository{

    @Override
    public Optional<List<CrazyAirRequest>> getCrazyAir(CrazyAirRequest crazyAirRequest) {
        return Optional.empty();
    }
}