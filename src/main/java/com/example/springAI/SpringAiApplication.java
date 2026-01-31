package com.example.springAI;

import com.example.springAI.service.MessageWindowChatMemoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class SpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiApplication.class, args);
    }

    // This part for only MessageWindowChatMemory
    // that case I won't use I will comment
//    @Component
//    public static class CommandLineAppStarupRunner implements CommandLineRunner {
//
//        private final MessageWindowChatMemoryService chatService;
//
//        public CommandLineAppStarupRunner(MessageWindowChatMemoryService chatService) {
//            this.chatService = chatService;
//        }
//
//        @Override
//        public void run(String... args) throws Exception {
//            chatService.startChat();
//        }
//    }

}
