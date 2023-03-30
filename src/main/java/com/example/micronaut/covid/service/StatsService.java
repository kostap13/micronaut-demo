package com.example.micronaut.covid.service;

import com.example.micronaut.covid.client.CovidClient;
import com.example.micronaut.covid.model.CountryStat;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class StatsService {

    private final CovidClient covidClient;

    private final CacheService cacheService;

    public StatsService(CovidClient covidClient, CacheService cacheService) {
        this.covidClient = covidClient;
        this.cacheService = cacheService;
    }

    /**
     * Load Stats for given countries since provided date
     * @param countries
     * @param startDate
     * @return
     */
    public Map<String, List<CountryStat>> loadCountriesData(List<String> countries, LocalDate startDate, LocalDate endDate) {
        final ConcurrentHashMap<String, List<CountryStat>> countryStats = new ConcurrentHashMap<>();
        countries.stream().parallel().forEach(country -> {
            List<CountryStat> stats = cacheService.get(country);
            if (stats == null) {
                stats = covidClient.fetchForCountry(country);
                cacheService.put(country, stats);
            }
            countryStats.put(
                    country,
                    stats.stream().filter(countryStat ->
                            (countryStat.getDate().isAfter(startDate) || countryStat.getDate().isEqual(startDate))
                                    && countryStat.getDate().isBefore(endDate.plusDays(1))
                    ).toList()
            );
        });
        return countryStats;
    }

}
