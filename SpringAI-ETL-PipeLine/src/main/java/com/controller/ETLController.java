package com.controller;


import com.service.ETLReaderService;
import com.service.ETLTransformerService;
import com.service.ETLWriterService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
public class ETLController {

    @Autowired
    ETLReaderService etlReaderService;

    @Autowired
    ETLTransformerService etlTransformerService;

    @Autowired
    ETLWriterService etlWriterService;

    @GetMapping("/etl")
    public String etl() {
        List<Document> documents = etlReaderService.loadTextasDocuments();
        List<Document> transformedDocuments = etlTransformerService.transform(documents);
        etlWriterService.write(transformedDocuments);
        return "ETL Pipeline completed";
    }
}
