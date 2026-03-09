package com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PGVectorStore {

    @Autowired
    VectorStore vectorStore;

    public void getDocument() {
        List<Document> documents = List.of(
                // Map.of() - This is factory method introduced in Java 9 to quickly create immutable maps
                new Document("The iPhone 15 features a powerful A16 Bionic chip and improved camera system.",
                        Map.of("brand", "Apple", "model", "iPhone 15", "year", 2023)),

                new Document("The iPhone 14 introduced crash detection and satellite emergency SOS.",
                        Map.of("brand", "Apple", "model", "iPhone 14", "year", 2022)),

                new Document("The iPhone 13 is known for its strong battery life and A15 Bionic processor.",
                        Map.of("brand", "Apple", "model", "iPhone 13", "year", 2021)),

                new Document("The iPhone 12 was the first iPhone to support 5G connectivity.",
                        Map.of("brand", "Apple", "model", "iPhone 12", "year", 2020)),

                new Document("The iPhone 11 introduced the dual camera system and improved night photography.",
                        Map.of("brand", "Apple", "model", "iPhone 11", "year", 2019)),

                new Document("The iPhone SE (3rd generation) uses the A15 Bionic chip, supports 5G connectivity, and keeps the classic Touch ID design.",
                        Map.of("brand", "Apple", "model", "iPhone SE 3", "year", 2022)),

                new Document("The MacBook Air M1 introduced Apple Silicon with the M1 chip, delivering major improvements in performance and battery efficiency.",
                        Map.of("brand", "Apple", "model", "MacBook Air M1", "year", 2020, "category", "Laptop")));

        log.info("Size of Collection: {}", documents.size());
        TextSplitter splitter = new TokenTextSplitter();
        documents.forEach(document -> {
            List<Document> splittedDoc = splitter.split(List.of(document));
            vectorStore.add(splittedDoc);
            log.info("Document added: {}", document);
        });

    }

    public List<Document> searchDocuments(String query) {
        List<Document> result = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(1).build());
        return result;
    }

}
