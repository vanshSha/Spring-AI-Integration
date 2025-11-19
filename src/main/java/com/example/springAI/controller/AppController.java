package com.example.springAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    // logger
    public final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AppController.class);

    @Autowired
    ChatClient chatClient;



    @PostMapping("/chat")
    public String chat(@RequestBody String request) {
        logger.info("Received request to chat");
        String response = chatClient.prompt()
                .user(request)  // "Hello, how can i help you today ? "
                .call()
                .content();
        logger.info("Response from chat client : {}", response);
        return response;
    }
}
