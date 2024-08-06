package com.asecurityguru.ollamarestapi.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import java.util.function.Function;

@Configuration
public class WeatherFunctionConfig {

    private final WeatherApiProperties weatherConfigProperties;

    public WeatherFunctionConfig(WeatherApiProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
    }

    @Bean
    @Description("Provides a function to fetch weather information for a specified city.")
    public Function<WeatherDataService.Request, WeatherDataService.Response> weatherFunction() {
        // Validate configuration properties
        validateWeatherConfigProperties(weatherConfigProperties);

        // Create and return a new WeatherService instance
        return new WeatherDataService(weatherConfigProperties);
    }

    private void validateWeatherConfigProperties(WeatherApiProperties properties) {
        if (properties.getApiUrl() == null || properties.getApiUrl().isEmpty()) {
            throw new IllegalArgumentException("API URL must be provided.");
        }
        if (properties.getApiKey() == null || properties.getApiKey().isEmpty()) {
            throw new IllegalArgumentException("API key must be provided.");
        }
    }
}
