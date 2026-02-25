package com.controller;


import com.model.RefinedResponse;
import com.service.EvaluatorOptimizer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Mistral
//@RestController
public class EvaluatorOptimizerController {
    ChatClient chatClient;

    public EvaluatorOptimizerController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /*RefinedResponse -  represent an improved version of the Original AI after applying
    refinement process
    EvaluatorOptimizer - This is use for check quality if bad -> improve -> repeat


     */
    @PostMapping("/process")
    public RefinedResponse processInput(@RequestBody String userInput) {
        RefinedResponse refinedResponse = new EvaluatorOptimizer(chatClient).loop("""
                <user input>
                """ + userInput + """
                </user input>
                """);
        System.out.println("FINAL OUTPUT:\n : " + refinedResponse);
        return refinedResponse;
    }
}
