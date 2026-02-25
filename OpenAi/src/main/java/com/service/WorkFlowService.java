package com.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowService {

    @Autowired
    ChatClient chatClient;

    public WorkFlowService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    // Simple Work Flow of Agentic AI
    String[] workflowSteps = {
            "Summarize the following text in 2-3 sentences.",
            "Extract 3-5 key points from the text as bullet points.",
            "Generate 3 thoughtful follow-up questions about the content."
    };

    public String chain(String userInput) {
        String response = userInput;
        for (String prompt : workflowSteps) {
            String input = String.format("{%s}\n {%s}", prompt, response);
            //call()- sends the request to the AI model  // content()-  Get the text response for AI
            response = chatClient.prompt(input).call().content();
            // print the response for debugging
            System.out.println("Response: " + response);
        }
        return response;
    }
}
