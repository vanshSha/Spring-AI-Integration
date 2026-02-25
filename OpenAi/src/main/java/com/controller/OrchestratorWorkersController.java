package com.controller;


import com.service.OrchestratorWorkers;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorWorkersController {



    ChatClient chatClient;

    public OrchestratorWorkersController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/process")
    public OrchestratorWorkers.FinalResponse processTask(@RequestBody String taskDescription) {
        return new OrchestratorWorkers(chatClient).process(taskDescription);
    }


}
