package com.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLReaderService {


    @Value("classpath:hello.txt")
    Resource resource;

    /* Document is a data container used by Spring AI to Hold
    - Text
    - Metadata (optional information about the text) */
    public List<Document> loadTextasDocuments() {
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.get();
        return documents;
    }
}
