package com.example.weatherapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Represents the weather forecast for a city, including the city itself and a list of daily forecasts.
 */
public record CityWeatherForcast(
        @Schema(description = "Name of the city for which the forecast is provided.")
        City city,
        @Schema(description = "List of weather forecasts for consecutive days.")
        List<DayForecast> forecasts) { }
