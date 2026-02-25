package com.controller;

import com.metadataenrichment.ETLTransformerService1;
import com.metadataenrichment.ETLWriterService1;
import com.service.*;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ETLPDFReaderJSONController {

    @Autowired
    ETLPdfReaderService etlPdfReaderService;

    @Autowired
    ETLTransformerService1 etlTransformerService1;

    @Autowired
    ETLWriterService1 etlWriterService1;


    @Autowired
    ETLJSONReaderService etlJSONReaderService;

    @Autowired
    ETLJSONTransformerService etlJSONTransformerService;

    @Autowired
    ETLMDReader etlMDReader;

    @Autowired
    ETLMDTransformerService etlMDTransformerService;


    @GetMapping("/etlpdf")
    public String etlPdf() {
        List<Document> documents = etlPdfReaderService.loadPdfDocuemnts();
        List<Document> transformedDocuments = etlTransformerService1.transform(documents);
        etlWriterService1.write(transformedDocuments);
        return "ETL PDF Pipeline completed";
    }

    @GetMapping("/etlJson")
    public String etlJson() {
        List<Document> documents = etlJSONReaderService.loadJSONasDocuemnts();
        List<Document> transformedDocuments = etlJSONTransformerService.transform(documents);
        etlWriterService1.write(transformedDocuments);
        return "ETL JSON Pipeline completed";
    }

    @GetMapping("/etlMd")
    public String etlMd() {
        List<Document> documents = etlMDReader.loadMarkDownDocuments();
        List<Document> transformedDocuments = etlMDTransformerService.tranform(documents);
        etlWriterService1.write(transformedDocuments);
        return "ETL MD Pipeline completed";
    }
}
