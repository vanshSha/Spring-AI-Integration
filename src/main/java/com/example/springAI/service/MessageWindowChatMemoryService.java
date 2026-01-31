package com.example.springAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;

import java.util.Scanner;
@Service
public class MessageWindowChatMemoryService {

    MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.
            builder().
            maxMessages(10).
            build();

    private final ChatClient chatClient;


    public MessageWindowChatMemoryService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build()).build();
    }

    public String getResponse(String message) {
        return chatClient.prompt().user(message).call().content();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your message: ");
        while (true) {
            String message = scanner.nextLine();
            if (message.equals("exit")) {
                System.out.println("Exiting chat...");
                break;
            }
            String response = getResponse(message);
            System.out.println("Bot: " + response);
        }
        scanner.close();
    }
}
