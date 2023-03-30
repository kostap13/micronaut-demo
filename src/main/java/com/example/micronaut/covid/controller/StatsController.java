package com.example.micronaut.covid.controller;

import com.example.micronaut.covid.model.dto.StatResultDTO;
import com.example.micronaut.covid.service.AggregatorService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller("/api/stats/")
public class StatsController {

    private final AggregatorService aggregatorService;

    public StatsController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @Get(uri="/{date}{?countries}")
    public StatResultDTO byDate(
            @PathVariable LocalDate date,
            @QueryValue @NotBlank Optional<String> countries) {
        List<String> countriesList =  Arrays.asList(countries.get().split(","));
        return aggregatorService.aggregate(
                countriesList,
                date,
                date
        );
    }

    @Get(uri="/{startDate}/{endDate}{?countries}")
    public StatResultDTO byPeriod(
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate,
            @QueryValue @NotBlank Optional<String> countries) {
        List<String> countriesList =  Arrays.asList(countries.get().split(","));
        return aggregatorService.aggregate(
                countriesList,
                startDate,
                endDate
        );
    }
}
