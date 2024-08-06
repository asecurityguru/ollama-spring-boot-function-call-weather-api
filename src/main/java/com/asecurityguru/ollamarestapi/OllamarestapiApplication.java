package com.asecurityguru.ollamarestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.asecurityguru.ollamarestapi.functions.WeatherApiProperties;

@EnableConfigurationProperties(WeatherApiProperties.class)
@SpringBootApplication
public class OllamarestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OllamarestapiApplication.class, args);
	}

}
