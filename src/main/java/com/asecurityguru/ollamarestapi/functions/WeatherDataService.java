package com.asecurityguru.ollamarestapi.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;

@Validated
public class WeatherDataService implements Function<WeatherDataService.Request, WeatherDataService.Response> {

    private static final Logger log = LoggerFactory.getLogger(WeatherDataService.class);
    private final RestClient restClient;
    private final WeatherApiProperties weatherProps;

    public WeatherDataService(WeatherApiProperties props) {
        this.weatherProps = props;

        // Validate the configuration properties
        validateWeatherConfigProperties(weatherProps);

        log.info("Weather API URL: {}", weatherProps.getApiUrl());
        log.info("Weather API Key: {}", weatherProps.getApiKey());
        this.restClient = RestClient.create(weatherProps.getApiUrl());
    }

    @Override
    public Response apply(Request weatherRequest) {
        log.info("Weather Request: {}", weatherRequest);

        try {
            Response response = restClient.get()
                    .uri("/current.json?key={key}&q={q}", weatherProps.getApiKey(), weatherRequest.city())
                    .retrieve()
                    .body(Response.class);

            log.info("Weather API Response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error fetching weather data", e);
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }

    private void validateWeatherConfigProperties(WeatherApiProperties properties) {
        if (properties.getApiUrl() == null || properties.getApiUrl().isEmpty()) {
            throw new IllegalArgumentException("API URL must be provided.");
        }
        if (properties.getApiKey() == null || properties.getApiKey().isEmpty()) {
            throw new IllegalArgumentException("API key must be provided.");
        }
    }

    // Records to map API responses
    public record Request(String city) {}
    public record Response(Location location, Current current) {}
    public record Location(String name, String region, String country, Long lat, Long lon) {}
    public record Current(String temp_f, Condition condition, String wind_mph, String humidity) {}
    public record Condition(String text) {}
}
