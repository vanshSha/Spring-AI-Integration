package com.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AIResponse;
import com.model.VerificationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HallucinationService {

    private final ChatClient chatClient;
    private final VerificationService verificationService;
    @Autowired
    private final ObjectMapper objectMapper;

    public VerificationResult ask(String question) {
        log.info("Pipeline START | Question: {}", question);

        // Step 1 + Step 2
        // RAG + Prompt Engineering
        log.info("Calling ChatClient with RAG advisor and engineered system prompt...");

        String rawJson = chatClient
                .prompt()        // Prompt sent into AI
                // It's just a builder starter. Nothing happens, nothing is sent.
                //  a blank slate where you start attaching pieces like .system(), .user(), .advisors()

                .user(question) // Here's the actual question from Human side

                .call()        // send the request to the AI model(Send HTTP request to the LLM)

                .content();   // get text response from the AI

        log.info("Raw LLM response: {}", rawJson);

        AIResponse aiResponse = parseAIResponse(rawJson, question);
        log.info("Parsed → answerable={}, basedOn={}", aiResponse.answerable(), aiResponse.basedOn());

        // Verification
        VerificationResult result = verificationService.verify(question, aiResponse);
        log.info("Verified → answerable={}", result.answerable());

        log.info("Pipeline END");
        return result;
    }

    // Safely parse the AIResponse JSON — fallback on malformed output
    private AIResponse parseAIResponse(String raw, String question) {
        try {
            String cleaned = raw
                    .replaceAll("(?s)```json\\s*", "")
                    .replaceAll("(?s)```", "")
                    .trim();
            return objectMapper.readValue(cleaned, AIResponse.class);
        } catch (Exception e) {
            log.error("Failed to parse AIResponse JSON: {}", e.getMessage());
            return new AIResponse(
                    "I don't have enough information to answer this.",
                    "Parse error — model did not return valid JSON",
                    false
            );
        }
    }
}
