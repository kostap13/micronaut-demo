package com.example.micronaut.covid.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CountryResultDTO {

    private String country;
    private Integer cases;
    private LocalDate date;

    @JsonCreator
    public CountryResultDTO(String country, Integer cases, LocalDate date) {
        this.country = country;
        this.cases = cases;
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public Integer getCases() {
        return cases;
    }

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }
}
