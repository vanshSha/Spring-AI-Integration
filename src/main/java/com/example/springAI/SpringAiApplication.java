package com.example.springAI;

import com.example.springAI.service.MessageWindowChatMemoryService;
import jakarta.annotation.Resource;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiApplication.class, args);
    }


    // This part for only MessageWindowChatMemory
    // that case I won't use I will comment
    // @Component
    public static class CommandLineAppStartupRunner implements CommandLineRunner {

        private final MessageWindowChatMemoryService chatService;

        public CommandLineAppStartupRunner(MessageWindowChatMemoryService chatService) {
            this.chatService = chatService;
        }

        @Override
        public void run(String... args) throws Exception {
            chatService.startChat();
        }
    }

}
