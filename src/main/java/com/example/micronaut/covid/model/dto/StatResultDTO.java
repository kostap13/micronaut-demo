package com.example.micronaut.covid.model.dto;

public class StatResultDTO {

    private CountryResultDTO min;

    private CountryResultDTO max;

    public StatResultDTO(CountryResultDTO min, CountryResultDTO max) {
        this.min = min;
        this.max = max;
    }

    public CountryResultDTO getMin() {
        return min;
    }

    public CountryResultDTO getMax() {
        return max;
    }
}
