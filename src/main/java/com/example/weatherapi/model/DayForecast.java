package com.example.weatherapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;


/**
 * Represents the forecast data for a single day.
 */
@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayForecast {

    @Schema(description = "Date of the forecast.")
    private String date;
    @Schema(description = "Maximum temperature in Celsius.")
    private float maxTempC;
    @Schema(description = "Minimum temperature in Celsius.")
    private float minTempC;
    @Schema(description = "Average temperature in Celsius.")
    private float avgTempC;
    @Schema(description = "Maximum wind speed in kilometers per hour.")
    private float maxWindKph;
    @Schema(description = "Total precipitation in millimeters.")
    private float totalPrecipMm;
    @Schema(description = "Total snowfall in centimeters.")
    private float totalSnowCm;
    @Schema(description = "Average visibility in kilometers.")
    private float avgVisKm;
    @Schema(description = "Average humidity.")
    private float avgHumidity;
    @Schema(description = "UV index.")
    private float uv;

    @Hidden
    @JsonProperty("day")
    private void unpackNested(Map<String, Object> day){

        this.maxTempC = ((Number) day.get("maxtemp_c")).floatValue();
        this.minTempC = ((Number) day.get("mintemp_c")).floatValue();
        this.avgTempC = ((Number) day.get("avgtemp_c")).floatValue();
        this.maxWindKph = ((Number) day.get("maxwind_kph")).floatValue();
        this.totalPrecipMm = ((Number) day.get("totalprecip_mm")).floatValue();
        this.totalSnowCm = ((Number) day.get("totalsnow_cm")).floatValue();
        this.avgVisKm = ((Number) day.get("avgvis_km")).floatValue();
        this.avgHumidity = ((Number) day.get("avghumidity")).floatValue();
        this.uv = ((Number) day.get("uv")).floatValue();
    }
}
