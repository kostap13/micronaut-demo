package com.example.micronaut.covid.service;

import com.example.micronaut.covid.model.CountryStat;
import com.example.micronaut.covid.model.dto.CountryResultDTO;
import com.example.micronaut.covid.model.dto.StatResultDTO;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.util.*;

@Singleton
public class AggregatorService {

    private final StatsService statsService;

    public AggregatorService(StatsService statsService) {this.statsService = statsService;}

    public StatResultDTO aggregate(List<String> countries, LocalDate startDate, LocalDate endDate) {
        final LocalDate previousDate = startDate.minusDays(1);
        Map<String, List<CountryStat>> countryStats = statsService.loadCountriesData(countries, previousDate, endDate);
        return getMinMaxStats(countryStats,startDate);
    }

    private StatResultDTO getMinMaxStats(Map<String, List<CountryStat>> countryStats,  LocalDate date) {
        Integer minCases = Integer.MAX_VALUE;
        Integer maxCases = Integer.MIN_VALUE;
        CountryResultDTO min = null;
        CountryResultDTO max = null;

        for (Map.Entry<String, List<CountryStat>> entry: countryStats.entrySet()) {
            List<CountryStat> stats = entry.getValue();
            if (stats == null || stats.isEmpty()) continue;

            Integer previousCases = getFirstCase(stats, date);
            List<CountryStat> statsList = sortByDate(stats);

            for (CountryStat stat: statsList) {
                if (stat.getDate().isBefore(date)) continue;

                Integer newCases = stat.getCases() - previousCases;
                if (newCases < 0) newCases = 0;
                previousCases = stat.getCases();

                if (newCases > maxCases) {
                    maxCases = newCases;
                    max = new CountryResultDTO(stat.getCountry(), newCases, stat.getDate());
                }

                if (newCases < minCases) {
                    minCases = newCases;
                    min = new CountryResultDTO(stat.getCountry(), newCases, stat.getDate());
                }
            }
        }
        return new StatResultDTO(min, max);
    }

    private Integer getFirstCase(List<CountryStat> stats, LocalDate date) {
        final LocalDate previousDate = date.minusDays(1);
        Optional<CountryStat> statBeforeSearch =  stats.stream()
                .filter(stat -> stat.getDate().isEqual(previousDate))
                .findFirst();

        if (statBeforeSearch.isPresent()) return statBeforeSearch.get().getCases();

        return sortByDate(stats).get(0).getCases();
    }

    private List<CountryStat> sortByDate(List<CountryStat> stats) {
        List<CountryStat> list = new ArrayList<>(stats); // Workaround to prevent Unsupported exceptions with unmodifiableList.
        list.sort(Comparator.comparing(CountryStat::getDate));
        return list;
    }

}
