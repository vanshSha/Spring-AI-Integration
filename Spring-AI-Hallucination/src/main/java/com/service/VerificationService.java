package com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
public class VerificationService {


    private final ChatClient chatClient;

    @Autowired
    private final ObjectMapper objectMapper;

    // 3rd Step Verification

    public VerificationResult verify(String question, AIResponse aiResponse) {

        log.info("Starting verification for question: {}", question);

        // if the model said itself  couldn't answer -> skip verification
        if (!aiResponse.answerable()) {
            log.info("Model marked as not answerable — skipping LLM verification.");
            return new VerificationResult(aiResponse.answer(), aiResponse.basedOn(), false);
        }

        // Making Verification Prompt - asks a 'judge' to re-check the answer
        String verificationPrompt = buildVerificationPrompt(question, aiResponse);

        // system it is use for set role and rules of the AI models before process user's question
        String raw = chatClient.prompt().system("""
                
                 You are a strict fact-checking judge.
                 You will be given a QUESTION, an ANSWER, and a BASED_ON source detail.
                 
                 Your ONLY job is to decide if the ANSWER is directly supported by BASED_ON.
                 
                 RULES:
                 1. If the ANSWER matches what BASED_ON states — set answerable to true.
                 2. If the ANSWER adds, assumes, or extrapolates beyond BASED_ON — set answerable to false.
                 3. If the ANSWER is vague or unverifiable — set answerable to false.
                 
                 RESPONSE FORMAT — valid JSON only, no extra text:
                 {
                  "answer": "verified or corrected answer here",
                  "basedOn": "exact detail from BASED_ON that supports the answer",
                  "answerable": true or false
                 }   
                """)
                .user(verificationPrompt)
                .call()
                .content();
            log.info("Finished verification for question: {}", raw);
        return parseVerificationResult(raw, aiResponse);

    }

    //    Builds the user-turn message for the judge LLM
    private String buildVerificationPrompt(String question, AIResponse aiResponse) {
        return String.format("""
                        QUESTION: %s
                        
                        ANSWER: %s
                        
                        BASED_ON: %s
                        
                        Is this ANSWER fully supported by BASED_ON? Reply in JSON only.                 
                        """,
                question,
                aiResponse.answer(),
                aiResponse.basedOn());
    }

    // Parses judge response; falls back to original AIResponse on error
    private VerificationResult parseVerificationResult(String raw, AIResponse fallback) {
            try {
                String cleaned =  raw
                        .replaceAll("(?s)```json\\s*", "")
                        .replaceAll("(?s)```\\s*", "")
                        .trim();
                return objectMapper.readValue(cleaned, VerificationResult.class);
            } catch (Exception e) {
                log.error("Failed to parse verification response: {}", e.getMessage());
                return new  VerificationResult(fallback.answer(), fallback.basedOn(), fallback.answerable());
            }
       }
}
