package com.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ParallelWorkFlowMistralService {

    private final ChatClient chatClient;

    public ParallelWorkFlowMistralService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<String> parallel(String prompt, List<String> inputs, int nWorkers) {

        ExecutorService executor = Executors.newFixedThreadPool(nWorkers);
        try {
            List<CompletableFuture<String>> futures = inputs.stream()
                    .map(input -> CompletableFuture.supplyAsync(() -> {
                        try {
                            return chatClient.prompt(prompt + "\nInput: " + input).call().content();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process input: " + input, e);
                        }
                    }, executor))
                    .toList();

            // Wait for all tasks to complete
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    futures.toArray(CompletableFuture[]::new));
            allFutures.join();

            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

        } finally {
            executor.shutdown();
        }
    }
}
