package com.controller;


import com.metadataenrichment.ETLReaderService1;
import com.metadataenrichment.ETLTransformerService1;
import com.metadataenrichment.ETLWriterService1;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//n@RestController
public class ETLEnrichmentController {

    @Autowired
    ETLReaderService1 etlReaderService;

    @Autowired
    ETLTransformerService1 etlTransformerService;

    @Autowired
    ETLWriterService1 etlWriterService;

    @GetMapping("/etlEnrichment")
    public String etl() {
        List<Document> documents = etlReaderService.loadTextasDocuments();
        List<Document> transformedDocuments = etlTransformerService.transform(documents);
        etlWriterService.write(transformedDocuments);
        return "ETL Pipeline completed";
    }
}
