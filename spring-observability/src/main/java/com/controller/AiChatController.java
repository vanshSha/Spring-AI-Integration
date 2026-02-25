package com.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiChatController {

   // @Autowired
    ChatClient chatClient;

    public AiChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "Tell me one good book for clear understanding") String query) {
        return chatClient.prompt().user(query).call().chatResponse().getResult().getOutput().getText();
    }

    @GetMapping("/test")
    public String test() {
        return "ok";
    }


}

// 1st-  http://localhost:8080/actuator
// 2nd- http://localhost:8080/actuator/metrics
// 3rd- case it show like this . gen_ai.client.operation","gen_ai.client.operation.active"
// 4th- then I will execute on web http://localhost:8080/actuator/metrics/gen_ai.client.operation  <-- instead of post man
// http://localhost:8080/actuator/prometheus

