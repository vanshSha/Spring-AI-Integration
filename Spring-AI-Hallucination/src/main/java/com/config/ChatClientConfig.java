package com.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

   // STEP 2 — PROMPT ENGINEERING

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore){
            return builder.defaultSystem(
                    // PROMPT ENGINEERING
                    """
                       STRICT RULES - you must follow ALL of these 
                       1. Answer ONLY using the context provided to you.
                       2. If the answer is NOT clearly in the context, set answerable to false and answer with exactly: "I don't have enough information to answer this." 
                       3. NEVER make up specs, model names, years, chip names, or features.
                       4. NEVER assume or extrapolate beyond what the context states.
                       5. In "basedOn", mention the exact product/detail from context you used.
                       
                             RESPONSE FORMAT — always respond as valid JSON only, no extra text:
                                                    {
                                                      "answer": "your answer here",
                                                      "basedOn": "which context detail you used",
                                                      "answerable": true or false
                                                    }    
                            """)
                                      // Tells the advisor **where** to search for documents.
                                     // `vectorStore` is your PGVector database — the same one you loaded docs into via `/load`.
                    // QuestionAnswerAdvisor -> Take use query and search in vector Store then retrieve relevant document then inject them into prompt
                    .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore)
                            .searchRequest(SearchRequest.builder().topK(1).similarityThreshold(0.78).build()).build())
                    .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
