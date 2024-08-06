package com.asecurityguru.ollamarestapi.functions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "weather")
@Validated
public class WeatherApiProperties {

    private String apiKey;
    private String apiUrl;

    // Getter for apiKey
    public String getApiKey() {
        return apiKey;
    }

    // Setter for apiKey
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    // Getter for apiUrl
    public String getApiUrl() {
        return apiUrl;
    }

    // Setter for apiUrl
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    // Optional: Override toString for better logging and debugging
    @Override
    public String toString() {
        return "WeatherConfigProperties{" +
                "apiKey='" + apiKey + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                '}';
    }
}
