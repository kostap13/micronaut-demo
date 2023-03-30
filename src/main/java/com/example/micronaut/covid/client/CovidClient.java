package com.example.micronaut.covid.client;

import com.example.micronaut.covid.model.CountryStat;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "covid")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
@Header(name = ACCEPT, value = "application/json")
public interface CovidClient {

    @Get("/country/{country}/status/confirmed")
    List<CountryStat> fetchForCountry(String country);

}
