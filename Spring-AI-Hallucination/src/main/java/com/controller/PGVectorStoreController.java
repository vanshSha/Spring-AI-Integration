package com.controller;

import com.model.VerificationResult;
import com.service.HallucinationService;
import com.service.PGVectorStore;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PGVectorStoreController {

    @Autowired
    private PGVectorStore vectorStore;

    @Autowired
    private HallucinationService hallucinationService;


    @GetMapping("/load")
    public void loadDocument(){
        vectorStore.getDocument();
    }

    //This GET Request Doesn't have feature of Hallucination
    @GetMapping("/search")
    public List<Document> searchDocuments(@RequestParam String query){
        return vectorStore.searchDocuments(query);
    }

    @GetMapping("/ask")
    public VerificationResult ask(@RequestParam String question){
       return hallucinationService.ask(question);
    }



}
