package com.example.weatherapi.utils;

import com.example.weatherapi.model.DayForecast;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonMapperTest {


    private static final String JSON_STRING = """
                 {
                            "forecast": {
                                "forecastday": [
                                    {
                                        "date": "2024-06-08",
                                        "to_ignore": 9990,
                                        "day": {
                                            "maxtemp_c": 25.0,
                                            "mintemp_c": 15.0,
                                            "avgtemp_c": 20.0,
                                            "maxwind_kph": 10.0,
                                            "maxtemp_c_should_be_ignored": 99.0,
                                            "mintemp_c_should_be_ignored": 99.0,
                                            "avgtemp_c_should_be_ignored": 99.0,
                                            "maxwind_kph_should_be_ignored": 99.0,
                                            "totalprecip_mm": 5.0,
                                            "totalsnow_cm": 0.0,
                                            "avgvis_km": 10.0,
                                            "avghumidity": 75.0,
                                            "uv": 5.0,
                                            "condition_that_should_be_ignored": {
                                               "text": "Partly Cloudy ",
                                               "icon": "//cdn.weatherapi.com/weather/64x64/day/116.png",
                                               "code": 1003
                                            }
                                        }
                                    },
                                    {
                                        "date": "2024-06-09",
                                        "day": {
                                            "maxtemp_c": 28.0,
                                            "mintemp_c": 17.0,
                                            "avgtemp_c": 22.0,
                                            "maxwind_kph": 12.0,
                                            "totalprecip_mm": 2.0,
                                            "totalsnow_cm": 0.0,
                                            "avgvis_km": 10.0,
                                            "avghumidity": 70.0,
                                            "uv": 6.0
                                        }
                                    }
                                ]
                            }
                        }
            """;

    /**
     * Tests the mapping of JSON data to a specified Java type using a JSON pointer.
     *
     * @throws IOException if an I/O error occurs during JSON processing.
     */
    @Test
    void shouldMapJsonCorrectly() throws IOException {
        JsonPointer pointer = JsonPointer.compile("/forecast/forecastday");

        List<DayForecast> dayForecasts = JsonMapper.mapJsonToType(JSON_STRING, new TypeReference<>() {}, pointer);

        assertEquals(2, dayForecasts.size());

        DayForecast firstForecast = dayForecasts.get(0);
        assertEquals("2024-06-08", firstForecast.getDate());
        assertEquals(25.0f, firstForecast.getMaxTempC());
        assertEquals(15.0f, firstForecast.getMinTempC());
        assertEquals(20.0f, firstForecast.getAvgTempC());
        assertEquals(10.0f, firstForecast.getMaxWindKph());
        assertEquals(5.0f, firstForecast.getTotalPrecipMm());
        assertEquals(0.0f, firstForecast.getTotalSnowCm());
        assertEquals(10.0f, firstForecast.getAvgVisKm());
        assertEquals(75.0f, firstForecast.getAvgHumidity());
        assertEquals(5.0f, firstForecast.getUv());

        DayForecast secondForecast = dayForecasts.get(1);
        assertEquals("2024-06-09", secondForecast.getDate());
        assertEquals(28.0f, secondForecast.getMaxTempC());
        assertEquals(17.0f, secondForecast.getMinTempC());
        assertEquals(22.0f, secondForecast.getAvgTempC());
        assertEquals(12.0f, secondForecast.getMaxWindKph());
        assertEquals(2.0f, secondForecast.getTotalPrecipMm());
        assertEquals(0.0f, secondForecast.getTotalSnowCm());
        assertEquals(10.0f, secondForecast.getAvgVisKm());
        assertEquals(70.0f, secondForecast.getAvgHumidity());
        assertEquals(6.0f, secondForecast.getUv());
    }

    /**
     * Tests the mapping of invalid JSON data to a specified Java type.
     * Expects an IOException to be thrown.
     */
    @Test
    void mapJsonToType_shouldThrowIOExceptionForInvalidJson() {
        String invalidJsonString = "{ invalid json }";
        JsonPointer pointer = JsonPointer.compile("/forecast/forecastday");

        assertThrows(IOException.class, () -> JsonMapper.mapJsonToType(invalidJsonString, new TypeReference<>() {}, pointer));
    }
}