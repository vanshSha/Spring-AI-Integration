package com.example.demo.controller;

import com.example.demo.service.WeaviateVectorService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

//@RestController
public class WeaviateController {

    //@Autowired
    WeaviateVectorService weaviateVectorService;

    @GetMapping("/load")
    public String load() {
        weaviateVectorService.loadData();
        return "Weaviate Data loaded";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam(defaultValue = "default query") String query) {
        List<Document> docs = weaviateVectorService.search(query);
        List<String> docContents = docs.stream()
                .map(Document::getText)
                .collect(Collectors.toList());
        return docContents;
    }
}