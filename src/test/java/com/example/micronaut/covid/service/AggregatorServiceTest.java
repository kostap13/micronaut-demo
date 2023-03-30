package com.example.micronaut.covid.service;

import com.example.micronaut.covid.model.CountryStat;
import com.example.micronaut.covid.model.dto.CountryResultDTO;
import com.example.micronaut.covid.model.dto.StatResultDTO;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
public class AggregatorServiceTest {

    @Inject
    AggregatorService aggregatorService;

    @Inject
    StatsService statsService;

    @Test
    void testCalculateMinMax() {
        List<String> countries = List.of("germany", "japan");
        LocalDate startDate = LocalDate.parse("2020-01-23");
        LocalDate endDate  = LocalDate.parse("2020-01-24");
        StatResultDTO expected = new StatResultDTO(
                new CountryResultDTO("Germany", 0, LocalDate.parse("2020-01-24")),
                new CountryResultDTO("Japan", 2, LocalDate.parse("2020-01-24"))
        );

        when(statsService.loadCountriesData(countries, startDate.minusDays(1), endDate))
                .then(invocation -> testStatsData());

        StatResultDTO result = aggregatorService.aggregate(countries, startDate, endDate);

        assertNotNull(result);
        assertNotNull(result.getMax());
        assertNotNull(result.getMin());

        assertEquals(expected.getMax().getCases(), result.getMax().getCases());
        assertEquals(expected.getMax().getCountry(), result.getMax().getCountry());
        assertEquals(expected.getMax().getDate(), result.getMax().getDate());

        assertEquals(expected.getMin().getCases(), result.getMin().getCases());
        assertEquals(expected.getMin().getCountry(), result.getMin().getCountry());
        assertEquals(expected.getMin().getDate(), result.getMin().getDate());
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
        data.put("germany", countryStatsForGermany);

        return data;
    }

    @MockBean(StatsService.class)
    StatsService covidClient() {
        return mock(StatsService.class);
    }

}
