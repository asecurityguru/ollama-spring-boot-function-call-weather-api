package com.asecurityguru.ollamarestapi;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaChatController {
    private final ChatClient chatClient;

    public OllamaChatController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/api/v1/chat")
    public String root() {
        try{
            return chatClient.prompt()
            .user("Tell the benefits of using Ollama")
            .call()
            .content();
        } catch(Exception e){
            e.printStackTrace();
            return "Unable to process prompts at this time.";
        }
        
    }
}
