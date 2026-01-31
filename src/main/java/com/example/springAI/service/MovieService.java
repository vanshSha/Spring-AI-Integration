package com.example.springAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final ChatClient chatClient;

    @Value("classpath:prompts/movie-template.st")
    private Resource movieResource; // This part also why am i using resource

    public MovieService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String movieInfo(String movieName) {
        return chatClient.prompt()
                .user(userSpec -> userSpec.text(movieResource)
                        .param("movieName", movieName))
                .call()
                .chatResponse().getResult().getOutput().getText();
    }
}
