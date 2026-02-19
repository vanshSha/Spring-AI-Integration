package vector_database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@Service
@Slf4j
public class Neo4jVectorService {

    //@Autowired
    VectorStore  neo4jVectorStore;

    public void load() {

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("country", "UK", "year", 2020)),
                new Document("The World is Big and Salvation Lurks Around the Corner", Map.of("country", "BG", "year", 2018)),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("country", "NL", "year", 2023)),
                new Document("Exploring the depths of the ocean is like diving into a new world of wonder.", Map.of("country", "USA", "year", 2019)),
                new Document("Technology shapes the future but leaves our past behind.", Map.of("category", "Technology")),
                new Document("The evolution of artificial intelligence is transforming industries.", Map.of("category", "Technology", "year", 2021)),
                new Document("Mountains are the beginning and the end of all natural scenery.", Map.of("country", "CH", "year", 2022)),
                new Document("Books are a uniquely portable magic.", Map.of("author", "Stephen King", "genre", "Literature")),
                new Document("The stars are not afraid of the darkness; they only shine brighter.", Map.of("country", "AU", "year", 2021))
        );

        TextSplitter splitter = new TokenTextSplitter();
        documents.forEach(document -> {
            List<Document> splittedDocuments = splitter.split(document);
            log.info("Before Adding document: " + document.getText());
            try {
                TimeUnit.SECONDS.sleep(20); // Sleep for 20 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            log.info("Adding document: " + document.getText());
            neo4jVectorStore.add(splittedDocuments);
        });
    }

    public List<Document> search(String query) {
        return neo4jVectorStore.similaritySearch(SearchRequest.builder().query(query).topK(3).build());
    }
}
