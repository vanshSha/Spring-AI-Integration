package com.example.springAI.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

//@Configuration
public class AppConfig {

    @Value("classpath:system_message.txt")
    Resource resource;


    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem(resource)
                .build();
    }


    // When I will not use JdbcChatMemoryRepository that case I will comment and normal bean I will comment out
//    @Autowired
//    JdbcChatMemoryRepository jdbcChatMemoryRepository;
//
//    @Bean
//    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
//        ChatMemory chatMemory = MessageWindowChatMemory.builder()
//                .chatMemoryRepository(jdbcChatMemoryRepository)
//                .maxMessages(10) // Set the maximum number of messages to retain in memory
//                .build();
//        return chatClientBuilder
//                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
//                .build();
//    }
}
