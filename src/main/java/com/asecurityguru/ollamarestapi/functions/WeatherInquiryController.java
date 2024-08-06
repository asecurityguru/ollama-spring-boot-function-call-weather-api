package com.asecurityguru.ollamarestapi.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherInquiryController {
    private static final Logger log = LoggerFactory.getLogger(WeatherInquiryController.class);
    private final ChatClient chatClient;

    public WeatherInquiryController(ChatClient.Builder chatClientbuilder) {
        this.chatClient = chatClientbuilder
                .defaultFunctions("weatherFunction")
                .build();
    }

    @GetMapping("/api/v1/cityweather")
    public String cityWeather(@RequestParam String message) {
        try {
            log.info("Received message: {}", message);

            // Call the AI model with the user message
            String response = chatClient.prompt()
                    .user(message)
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
