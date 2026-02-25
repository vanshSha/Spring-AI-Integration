package com.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLMDReader {

    @Value("classpath:input.md")
    Resource resource;

    /*
    MarkdownDocumentReaderConfig - is a configuration class used in Spring AI when I need to read Markdown .md files using MarkdownDocumentReader
    withIncludeCodeBlock(false) - It remove code form your Markdown before sending it to the AI or Vectore Store
    If I set True Doesn't remove code

    MarkdownDocumentReader - is a class in Spring AI used to read Markdown (.md) files and convert them into Documents objects .
     */
    public List<Document> loadMarkDownDocuments() {
        MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                .withHorizontalRuleCreateDocument(true) // is used when reading text files to split the content into multiple
                // withHorizontalRuleCreateDocument
                .withIncludeCodeBlock(false) //
                .withAdditionalMetadata("filename", "input.md")
                .build();
        MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
        return reader.get();
    }
}
