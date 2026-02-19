package vector_database.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Service
@Slf4j
public class QdrantVectorService {

    @Autowired
    VectorStore vectorStore;


    @Value("classpath:products.json")
    Resource resource;

    public void loadProducts() {
        log.info("Loading products from " + resource.getFilename());
        List<Document> documents = readAndPrintJsonFile();
        TextSplitter textSplitter = new TokenTextSplitter();
        for (Document document : documents) {
           List<Document> splitDocuments = textSplitter.split(document);
                log.info("before adding document into vector store: " + document.getText());
                vectorStore.add(splitDocuments);
                log.info("Added document to vector store: " + document.getText());
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Finished loading products");
    }

    public List<Document> readAndPrintJsonFile() {
        List<Document> documents = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            for (JsonNode node : jsonNode) {
                if (node.has("description")) {
                    System.out.println(node.get("description").toString());
                    documents.add(new Document(node.get("description").toString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documents;
    }

    public List<Document> search(String query) {
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(2).similarityThreshold(0.7).build());
        return results;
    }

}
