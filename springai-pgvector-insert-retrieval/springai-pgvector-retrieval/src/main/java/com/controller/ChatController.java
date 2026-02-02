package com.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    private final VectorStore vectorStore;

    public ChatController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /*builder(): Builder is a design pattern used to construct complex objects step by step,allowing readable, flexible, and safe object creation without large constructors.*/
    @GetMapping("/API")
    public String chat() {
        SearchRequest searchRequest = SearchRequest.builder()
                .query("smartwatch with features like fitness tracking and health monitoring").topK(10).build();

        List<Document> results = vectorStore.similaritySearch(searchRequest);

        return results.stream()
                .map(Document::getText)
                .limit(10)
                .collect(Collectors.joining(", "));
    }
}
