package com.metadataenrichment;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.writer.FileDocumentWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLWriterService1 {
    public void write(List<Document> documents) {
        FileDocumentWriter writer = new FileDocumentWriter("output.txt",false, MetadataMode.ALL,false);
        writer.accept(documents);

        /*WithDocumentMaker - is used to customize how a Document object is created while reading content from a source (PDF, Text, etc).
         MetadataMode.ALL - is used to control how metadata is handled when a Document is converted into text for embeddings or for
         sending LLM */
    }
}
