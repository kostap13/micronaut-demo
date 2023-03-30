package com.example.micronaut.covid.service;

import com.example.micronaut.covid.client.CovidClient;
import com.example.micronaut.covid.model.CountryStat;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import io.micronaut.test.annotation.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MicronautTest
@Requires(property = "mockito.test.enabled")
public class StatsServiceTest {

    @Inject
    StatsService statsService;

    @Inject
    @Client("/")
    CovidClient covidClient;

    @Inject
    CacheService cacheService;

    @Test
    void testReceivingData() {
        List<String> countries = List.of("germany", "japan");
        LocalDate startDate = LocalDate.parse("2020-01-23");
        LocalDate endDate  = LocalDate.parse("2020-01-24");

        when(covidClient.fetchForCountry(countries.get(0)))
                .then(invocation -> testStatsData().get(countries.get(0)));
        when(covidClient.fetchForCountry(countries.get(1)))
                .then(invocation -> testStatsData().get(countries.get(1)));

        when(cacheService.get(countries.get(0)))
                .then(invocation -> testStatsData().get(countries.get(0)));
        when(cacheService.get(countries.get(1)))
                .then(invocation -> testStatsData().get(countries.get(1)));

        Map<String, List<CountryStat>> received = statsService.loadCountriesData(countries, startDate, endDate);

        verify(covidClient).fetchForCountry(countries.get(0));
        verify(covidClient).fetchForCountry(countries.get(1));

        Map<String, List<CountryStat>> receivedCached = statsService.loadCountriesData(countries, startDate, endDate);

        verify(cacheService).get(countries.get(0));
        verify(cacheService).get(countries.get(1));
    }

    private Map<String, List<CountryStat>> testStatsData() {
        Map<String, List<CountryStat>> data = new HashMap<>();
        List<CountryStat> countryStatsForJapan = new ArrayList<>();
        countryStatsForJapan.add(new CountryStat(
                "Japan",
                "JP",
                "",
                "",
                "",
                "36.2",
                "138.25",
                2,
                "confirmed",
                LocalDate.parse("2020-01-22")
        ));
        countryStatsForJapan.add(new CountryStat(
                "Japan",
                "JP",
                "",
                "",
                "",
                "36.2",
                "138.25",
                3,
                "confirmed",
                LocalDate.parse("2020-01-23")
        ));
        countryStatsForJapan.add(new CountryStat(
                "Japan",
                "JP",
                "",
                "",
                "",
                "36.2",
                "138.25",
                5,
                "confirmed",
                LocalDate.parse("2020-01-24")
        ));
        countryStatsForJapan.add(new CountryStat(
                "Japan",
                "JP",
                "",
                "",
                "",
                "36.2",
                "138.25",
                4,
                "confirmed",
                LocalDate.parse("2020-01-25")
        ));
        data.put("japan", countryStatsForJapan);

        List<CountryStat> countryStatsForGermany = new ArrayList<>();
        countryStatsForGermany.add(new CountryStat(
                "Germany",
                "DE",
                "",
                "",
                "",
                "51.17",
                "10.45",
                4,
                "confirmed",
                LocalDate.parse("2020-01-22")
        ));
        countryStatsForGermany.add(new CountryStat(
                "Germany",
                "DE",
                "",
                "",
                "",
                "51.17",
                "10.45",
                6,
                "confirmed",
                LocalDate.parse("2020-01-23")
        ));
        countryStatsForGermany.add(new CountryStat(
                "Germany",
                "DE",
                "",
                "",
                "",
                "51.17",
                "10.45",
                3,
                "confirmed",
                LocalDate.parse("2020-01-24")
        ));
        countryStatsForGermany.add(new CountryStat(
                "Germany",
                "DE",
                "",
                "",
                "",
                "51.17",
                "10.45",
                9,
                "confirmed",
                LocalDate.parse("2020-01-25")
        ));
        data.put("germany", countryStatsForGermany);

        return data;
    }

    @MockBean(CovidClient.class)
    CovidClient covidClient() {
        return mock(CovidClient.class);
    }

    @MockBean(CacheService.class)
    CacheService mathService() {
        return mock(CacheService.class);
    }
}
