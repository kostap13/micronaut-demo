package com.example.micronaut.covid.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class CountryStat {
    private String country;
    private String countryCode;
    private String province;
    private String city;
    private String cityCode;
    private String lat;
    private String lon;
    private Integer cases;
    private String status;
    private LocalDate date;

    @JsonCreator
    public CountryStat(
            @JsonProperty("Country") String country,
            @JsonProperty("CountryCode") String countryCode,
            @JsonProperty("Province") String province,
            @JsonProperty("City") String city,
            @JsonProperty("CityCode") String cityCode,
            @JsonProperty("Lat") String lat,
            @JsonProperty("Lon") String lon,
            @JsonProperty("Cases") Integer cases,
            @JsonProperty("Status") String status,
            @JsonProperty("Date")
            @JsonFormat
                    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'")
            LocalDate date
    ) {
        this.country = country;
        this.countryCode = countryCode;
        this.province = province;
        this.city = city;
        this.cityCode = cityCode;
        this.lat = lat;
        this.lon = lon;
        this.cases = cases;
        this.status = status;
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public Integer getCases() {
        return cases;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }
}
