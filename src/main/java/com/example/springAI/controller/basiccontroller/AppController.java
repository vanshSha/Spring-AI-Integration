package com.example.springAI.controller.basiccontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AppController {

    @Autowired
    private ChatClient chatClient;

    @PostMapping("/chat")
    public String chat(@RequestBody String request) {
        log.info("Received request to chat");

        String response = chatClient.prompt()
                .user(request)
                .call()
                .content();

        log.info("Response from chat client: {}", response);
        return response;
    }
}
