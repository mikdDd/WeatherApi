package com.example.weatherapi.controller;

import com.example.weatherapi.model.City;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherForecastControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests if a forecast for each city can be retrieved.
     * Verifies that the response status is 200 OK for each city.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldGetSingleCityForecastForeachCity() throws Exception {
        for(City city : City.values()){
            String url = String.format("/weather/%s",city.name());

            mockMvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().is(200));
        }

    }

    /**
     * Tests if the forecast for each city is valid.
     * Verifies that the response contains 3 elements (forecasts for 3 days) and fields are not null.
     * Also checks if the forecast dates match the expected values.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldSingleCityForecastBeValidForeachCity() throws Exception {
        for (City city : City.values()) {
            String url = String.format("/weather/%s", city.name());

            mockMvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].date",
                            is(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(new Date()))))
                    .andExpect(jsonPath("$[1].date",
                            is(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(DateUtils.addDays(new Date(), 1)))))
                    .andExpect(jsonPath("$[2].date",
                            is(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(DateUtils.addDays(new Date(), 2)))))
                    .andExpect(jsonPath("$[*]",everyItem(notNullValue())));
        }
    }

    /**
     * Tests if the forecast for all cities can be retrieved.
     * Verifies that the response status is 200 OK.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldGetAllCitiesForecast() throws Exception {
            String url = "/weather/";

            mockMvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().is(200));


    }

    /**
     * Tests if the forecast for all cities is valid.
     * Verifies that the response contains elements for each city and fields are not null.
     *
     * @throws Exception if an error occurs during request execution.
     */
    @Test
    void shouldAllCitiesForecastBeValid() throws Exception {

            String url = "/weather/";

            mockMvc.perform(get(url))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$", hasSize(City.values().length)))
                    .andExpect(jsonPath("$[*].city", is(oneOf(Arrays.stream(City.class.getEnumConstants()).map(Enum::name).collect(Collectors.toList())))))
                    .andExpect(jsonPath("$[*].forecast", everyItem(notNullValue())));
        }

}