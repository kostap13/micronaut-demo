package com.example.micronaut.covid.service;

import com.example.micronaut.covid.model.CountryStat;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MicronautTest
public class CacheServiceTest {

    @Inject
    CacheService cacheService;

    @Test
    void testAddDataToCache() {
        Map<String, List<CountryStat>> data =  testStatsData();

        for (Map.Entry<String, List<CountryStat>> entry: data.entrySet()) {
            cacheService.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, List<CountryStat>> entry: data.entrySet()) {
            Assertions.assertNotNull(
                    cacheService.get(entry.getKey())
            );
        }
    }

    @Test
    void testClearCache() {
        Map<String, List<CountryStat>> data =  testStatsData();

        for (Map.Entry<String, List<CountryStat>> entry: data.entrySet()) {
            cacheService.put(entry.getKey(), entry.getValue());
        }

        cacheService.clear();

        for (Map.Entry<String, List<CountryStat>> entry: data.entrySet()) {
            Assertions.assertNull(
                    cacheService.get(entry.getKey())
            );
        }
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
        data.put("germany", countryStatsForGermany);

        return data;
    }
}
