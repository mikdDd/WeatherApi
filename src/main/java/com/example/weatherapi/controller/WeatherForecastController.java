package com.example.weatherapi.controller;

import com.example.weatherapi.model.City;
import com.example.weatherapi.model.CityWeatherForcast;
import com.example.weatherapi.model.DayForecast;
import com.example.weatherapi.service.WeatherApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller handling weather forecast queries.
 */
@Tag(name = "Weather API", description = "API for retrieving weather forecasts")@RequiredArgsConstructor
@RestController
public class WeatherForecastController {
    private final WeatherApiService weatherApiService;

    /**
     * Retrieves weather forecast for a specific city.
     *
     * @param city The city for which the forecast is retrieved.
     * @return List of weather forecasts for consecutive days.
     */
    @Operation(summary = "Get weather forecast by city", description = "Retrieves weather forecast for a specific city.")
    @GetMapping("/weather/{city}")
    public List<DayForecast> getForecastByCity(@PathVariable City city) {
        return weatherApiService.getForecastForCity(city);
    }

    /**
     * Retrieves weather forecast for all cities.
     *
     * @return List of weather forecasts for all cities.
     */
    @Operation(summary = "Get weather forecast for all cities", description = "Retrieves weather forecast for all cities.")
    @GetMapping("/weather/")
    public List<CityWeatherForcast> getForecast() {
        return weatherApiService.getForecastForAllCities();
    }
}
