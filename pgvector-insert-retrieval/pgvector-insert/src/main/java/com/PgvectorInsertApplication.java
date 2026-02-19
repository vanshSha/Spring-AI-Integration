package com;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class PgvectorInsertApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PgvectorInsertApplication.class, args);
    }

    @Value("classpath:input.txt")
    Resource resource; // Use of this means data from

    private final VectorStore vectorStore;  // IT used to for store and search embeddings(vectors)

    // Here I am doing Dependency Injection through Constructor
    public PgvectorInsertApplication(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Document> documents = Files.lines(resource.getFile().toPath())
                .map(Document::new)
                .collect(Collectors.toList());
        TextSplitter textSplitter = new TokenTextSplitter();
        for (Document document : documents) {
            List<Document> splittedDocs = textSplitter.split(document);
            System.out.println("before adding document: " + document.getText());

            vectorStore.add(splittedDocs);
            System.out.println("Added document: " + document.getText());
            Thread.sleep(5000);
        }
    }
}
