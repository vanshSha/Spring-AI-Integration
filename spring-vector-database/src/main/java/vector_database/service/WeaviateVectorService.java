package vector_database.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//@Service
public class WeaviateVectorService {
   // @Autowired
    VectorStore vectorStore;

    @Value("classpath:products.json")
    Resource resource;


    public List<Document> loadData() {
        List<Document> documents = readAndPrintJsonFile();
        TextSplitter textSplitter = new TokenTextSplitter();

        for (Document document : documents) {
            List<Document> splitteddocs = textSplitter.split(document);

            try {
                // Sleep for 1 second
                vectorStore.add(splitteddocs);
                System.out.println("Added document: " + document.getText());
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        System.out.println("Transformed documents: " + documents);
        return documents;
    }

    public List<Document> search(String query) {
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(3).similarityThreshold(0.7).build());
        return results;
    }

    public List<Document> readAndPrintJsonFile() {
        List<Document> documents = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            for (JsonNode node : jsonNode) {
                if (node.has("description")) {
                    //System.out.println(node.get("description").toString());
                    documents.add(new Document(node.get("description").toString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return documents;
    }
}