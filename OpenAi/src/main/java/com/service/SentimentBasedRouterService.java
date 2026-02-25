package com.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// Routing Work Flow
@Service
public class SentimentBasedRouterService {


    private ChatClient chatClient;

    @Autowired
    private PositiveFeedbackHandler positiveFeedbackHandler;

    @Autowired
    private NegativeFeedbackHandler negativeFeedbackHandler;

    @Autowired
    private NeutralFeedbackHandler neutralFeedbackHandler;



    public SentimentBasedRouterService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }



    private static final PromptTemplate sentimentPrompt = new PromptTemplate("""
        Analyze the sentiment of the following user feedback.
        The possible sentiments are: 'positive', 'negative', 'neutral'.

        User Feedback: {feedback}

        Respond with the identified sentiment only.
        """);

    public void routeFeedback(String feedback) {
        System.out.println("Routing feedback: " + feedback);

        Message message = sentimentPrompt.createMessage(Map.of("feedback", feedback));
        // createMessage - this method use for convert PromptTemplate into a Message Object
        // that can be sent to the AI model (ChatModel/ChatClient)

        Prompt prompt = new Prompt(List.of(message));
        // Prompt is the object that represents that complete input sent to the AI model
        System.out.println("Prompt: " + prompt);

        String response =   chatClient.prompt(prompt).call().content();
        assert response != null;
        String sentiment = response.trim().toLowerCase();

        System.out.println("Identified Sentiment: " + sentiment);

        switch (sentiment) {
            case "positive":
                positiveFeedbackHandler.handle(feedback);
                break;
            case "negative":
                negativeFeedbackHandler.handle(feedback);
                break;
            case "neutral":
                neutralFeedbackHandler.handle(feedback);
                break;
            default:
                System.out.println("Could not determine sentiment for: " + feedback);
        }
    }

}


@Service
class PositiveFeedbackHandler {
    public void handle(String feedback) {
        System.out.println("Logging positive feedback: " + feedback);
        // Logic to store positive feedback for analysis or praise
    }
}

@Service
class NegativeFeedbackHandler {
    public void handle(String feedback) {
        System.out.println("Logging negative feedback: " + feedback);
        // Logic to store negative feedback for analysis or escalation
    }
}

@Service
class NeutralFeedbackHandler {
    public void handle(String feedback) {
        System.out.println("Logging neutral feedback: " + feedback);
        // Logic to store neutral feedback for analysis or follow-up
    }
}