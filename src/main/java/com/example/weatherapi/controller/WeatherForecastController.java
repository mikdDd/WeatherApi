package com.example.weatherapi.controller;

import com.example.weatherapi.model.City;
import com.example.weatherapi.model.CityWeatherForcast;
import com.example.weatherapi.model.DayForecast;
import com.example.weatherapi.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SimpleController {
    private final WeatherApiService weatherApiService;

    @GetMapping("/weather/{city}")
    public List<DayForecast> getForecastByCity(City city) throws IOException {
        return weatherApiService.getForecastForCity(city);
    }
    @GetMapping("/weather/")
    public List<CityWeatherForcast> getForecast() throws IOException {
        return weatherApiService.getForecastForAllCities();
    }
}
