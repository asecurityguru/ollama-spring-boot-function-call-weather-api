package com.asecurityguru.ollamarestapi.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.function.Function;

@RestController
public class WeatherInquiryController {
    private static final Logger log = LoggerFactory.getLogger(WeatherInquiryController.class);
    private final ChatClient chatClient;
    private final Function<WeatherDataService.Request, WeatherDataService.Response> weatherFunction;

     public WeatherInquiryController(ChatClient.Builder chatClientBuilder,
                                   Function<WeatherDataService.Request, WeatherDataService.Response> weatherFunction) {
         this.chatClient = chatClientBuilder.build();
         this.weatherFunction = weatherFunction;
    }

    @GetMapping("/api/v1/cityweather")
    public String cityWeather(@RequestParam String message) {
        try {

             // 1. Call weather API using the weatherFunction
            WeatherDataService.Request request = new WeatherDataService.Request(message);
            WeatherDataService.Response weatherResponse = weatherFunction.apply(request);

            // 2. Extract weather details
            String weatherDetails = String.format("Temperature: %s°F, Condition: %s, Humidity: %s",
                    weatherResponse.current().temp_f(),
                    weatherResponse.current().condition().text(),
                    weatherResponse.current().humidity());

            // 3. Pass weather details to Mistral
            String prompt = String.format("Given this weather data for %s: %s. Summarize for the user.",
                    message, weatherDetails);
            log.info("Prompting AI with: {}", prompt);

            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            log.info("AI response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error processing request", e);
            return "Unable to process your request at this time.";
        }
    }
}
