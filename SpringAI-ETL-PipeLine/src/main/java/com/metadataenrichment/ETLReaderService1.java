package com.metadataenrichment;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLReaderService1 {


    @Value("classpath:hello.txt")
    Resource resource;

    public List<Document> loadTextasDocuments() {
        TextReader textReader = new TextReader(resource);
        List<Document> documents = textReader.get();
        return documents;
    }
}
