package com.example.springAI.service;

import com.example.springAI.model.Answer;
import com.example.springAI.model.Question;
import jakarta.security.auth.message.callback.PasswordValidationCallback;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class RecipeGeneratorImpl implements RecipeGenerator {

    private final ChatClient chatClient;

    public RecipeGeneratorImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // This part also I will study later
    private final String recipeTemplate = """
            Answer for {foodname} for {question}?""";


    @Override
    public Answer generateRecipe(Question question) {
        return new Answer(getMessage(question).getResult().getOutput().getText());
    }

    // This part i will study
    private ChatResponse getMessage(Question question) {
        return chatClient.prompt()
                .user(userSpec -> userSpec.text(recipeTemplate)
                        .param("foodname", question.getFoodName())
                        .param("question", question.getQuestion()))
                .call()
                .chatResponse();
    }
}
