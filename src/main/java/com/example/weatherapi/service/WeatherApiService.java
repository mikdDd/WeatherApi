package com.example.weatherapi.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.ApisApi;
import org.threeten.bp.LocalDate;


@Service
//@RequiredArgsConstructor
public class WeatherApiClient {

    private final ApiClient defaultClient = Configuration.getDefaultApiClient();

    // Configure API key authorization: ApiKeyAuth
    private final ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("ApiKeyAuth");
//    apiKeyAuth.setApiKey("YOUR API KEY");
    // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
    //ApiKeyAuth.setApiKeyPrefix("Token");

    private final ApisApi apiInstance = new ApisApi();

    public WeatherApiClient(@Value("${weatherapi.auth.key}") String apiKey) throws ApiException {
        apiKeyAuth.setApiKey(apiKey);
    }


    public void getFromApi(){
        String q = "q_example"; // String | Pass US Zipcode, UK Postcode, Canada Postalcode, IP address, Latitude/Longitude (decimal degree) or city name. Visit [request parameter section](https://www.weatherapi.com/docs/#intro-request) to learn more.
        LocalDate dt = LocalDate.now(); // LocalDate | Date on or after 1st Jan, 2015 in yyyy-MM-dd format
        try {
            Object result = apiInstance.astronomy(q, dt);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ApisApi#astronomy");
            e.printStackTrace();
        }

    }

}
