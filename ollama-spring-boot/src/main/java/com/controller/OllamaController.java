package com.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {
    ChatClient chatClient;

    public OllamaController(ChatClient.Builder chatClientbuilder)  {
        this.chatClient = chatClientbuilder.build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message" ,defaultValue = "what is the capital of India ?") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse().getResult().getOutput().getText();
    }
}

// ollama --version

