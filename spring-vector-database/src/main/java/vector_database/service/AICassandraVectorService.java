package vector_database.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AICassandraVectorService {

   // @Autowired
    VectorStore vectorStore;

    public void getDocuments() {

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("country", "UK", "year", 2020)),
                new Document("The World is Big and Salvation Lurks Around the Corner", Map.of("country", "BG", "year", 2018)),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("country", "NL", "year", 2023)),
                new Document("Exploring the depths of the ocean is like diving into a new world of wonder.", Map.of("country", "USA", "year", 2019)),
                new Document("Technology shapes the future but leaves our past behind.", Map.of("category", "Technology")),
                new Document("The evolution of artificial intelligence is transforming industries.", Map.of("category", "Technology", "year", 2021)),
                new Document("Mountains are the beginning and the end of all natural scenery.", Map.of("country", "CH", "year", 2022)),
                new Document("Books are a uniquely portable magic.", Map.of("author", "Stephen King", "genre", "Literature")),
                new Document("The stars are not afraid of the darkness; they only shine brighter.", Map.of("country", "AU", "year", 2021)));

        log.info("Size of Collection : {}" , documents.size());
        TextSplitter splitter = new TokenTextSplitter();
        documents.forEach(doc -> {
            List<Document> splittedDoc = splitter.split(doc);
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            vectorStore.add(splittedDoc);
            log.info("Added document: {}", doc);
        });

    }

    public List<Document> searchDocument(String query) {
        List<Document> result = vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(2).build());
        return result;
    }

}
