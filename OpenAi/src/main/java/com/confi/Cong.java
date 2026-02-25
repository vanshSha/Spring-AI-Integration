package com.confi;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Cong {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClient) {
        return chatClient.build();
    }
}
