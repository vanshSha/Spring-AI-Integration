package com.example.demo.controller;

import com.example.demo.service.AICassandraVectorService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AICassandraController {

    @Autowired
    AICassandraVectorService aiCassandraVectorService;

    @GetMapping("/load")
    public void loadDocument() {
        aiCassandraVectorService.getDocuments();
    }

    @GetMapping("/search")
    public List<Document> searchDocument() {
      return   aiCassandraVectorService.searchDocument("Technology");
    }
}
