package com.metadataenrichment;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.transformer.SummaryMetadataEnricher;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLTransformerService1 {

    // ChatModel is A interface send prompt to LLM
    @Autowired
    ChatModel chatModel;

    public List<Document> transform(List<Document> documents) {
        // Split the documents into tokens
        TextSplitter splitter = new TokenTextSplitter();
        List<Document> splitDocuments = splitter.split(documents);

        // Enricher the documents with chat model and KeyWords
        // It is used to automatically extract important keyword from Document and Store them inside the document's metadata
        //KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(chatModel,5);
        //return keywordMetadataEnricher.apply(splitDocuments);

        // This is use for creating summary
        SummaryMetadataEnricher summaryMetadataEnricher = new SummaryMetadataEnricher(chatModel,List.of(SummaryMetadataEnricher.SummaryType.CURRENT));
        return summaryMetadataEnricher.apply(splitDocuments);
    }
}
