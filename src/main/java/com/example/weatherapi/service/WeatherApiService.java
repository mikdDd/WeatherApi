package com.example.weatherapi.service;

import com.example.weatherapi.model.City;
import com.example.weatherapi.model.CityWeatherForcast;
import com.example.weatherapi.model.DayForecast;
import com.example.weatherapi.utils.JsonMapper;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for interacting with the Weather API.
 */
@Service
public class WeatherApiService {

    private final String apiAuthKey;
    private final WebClient webClient;

    /**
     * Constructs a WeatherApiService with the specified API key and base URL.
     *
     * @param apiKey     The API key used for authentication.
     * @param apiBaseUrl The base URL of the Weather API.
     */
    public WeatherApiService(@Value("${weatherapi.auth.key}") String apiKey, @Value("${weatherapi.url}") String apiBaseUrl) {
        this.apiAuthKey = apiKey;
        this.webClient = WebClient.builder().baseUrl(apiBaseUrl).build();
    }

    /**
     * Retrieves the forecast for a specific city.
     *
     * @param city The city for which the forecast is retrieved.
     * @return A list of DayForecast objects representing the forecast for consecutive days.
     */
    public List<DayForecast> getForecastForCity(City city){
        int FORECAST_DURATION = 3;
        String url = String.format("/forecast.json?q=%s&days=%d&key=%s", city.name(), FORECAST_DURATION, apiAuthKey);

        Mono<String> response =  this.webClient.get().uri(url).retrieve().bodyToMono(String.class);

        String jsonObjects = response.block();

        List<DayForecast> forecasts = new ArrayList<>();

        try{
            forecasts = JsonMapper.mapJsonToType(jsonObjects, new TypeReference<>() {}, JsonPointer.compile("/forecast/forecastday"));
        } catch (IOException e){
            System.err.println("Exception when mapping JSON");
            e.printStackTrace();
        }

        return forecasts;
    }

    /**
     * Retrieves the forecast for all cities.
     *
     * @return A list of CityWeatherForcast objects representing the forecast for all cities.
     */
    public List<CityWeatherForcast> getForecastForAllCities() {
        List<CityWeatherForcast> l = new ArrayList<>();
        for (City city : City.values()){
            l.add(new CityWeatherForcast(city, getForecastForCity(city)));
        }
        return l;
    }
}
