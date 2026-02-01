package com.example.springAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatClient client;

    public String getChatResponse(String prompt) {
        return client.prompt(prompt)
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
    }
}
