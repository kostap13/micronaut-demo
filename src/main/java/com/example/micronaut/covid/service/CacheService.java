package com.example.micronaut.covid.service;

import com.example.micronaut.covid.model.CountryStat;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Add cache invalidation after some period
// TODO: Move to prod-ready solution. Usually, I've used Redis
@Singleton
public class CacheService {

    private final ConcurrentHashMap<String, List<CountryStat>> countriesData = new ConcurrentHashMap<>();

    public List<CountryStat> get(String key) {
        return countriesData.get(key);
    }

    public void put(String key, List<CountryStat> data) {
        countriesData.put(key,data);
    }

    public void clear() {
        countriesData.clear();
    }

}
