package com.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLPdfReaderService {

    @Value("classpath:AzureFunctionsOverview.pdf")
    Resource resource;

    public List<Document> loadPdfDocuemnts() {
        PagePdfDocumentReader pdfDocumentReader = new PagePdfDocumentReader(resource,
                PdfDocumentReaderConfig.builder()
                        .withPageBottomMargin(0)
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                // This method is used when reading documents (PDF or Text) to remove unwanted headers files from top
                                // of each page before creating Document objects .
                                .withNumberOfTopTextLinesToDelete(0).build())
                        // How many pages should be combined into a single Document object
                        .withPagesPerDocument(1)
                        .build());
        return pdfDocumentReader.read();
    }
}
