package vector_database.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

//@RestController
public class RedisController {

    //@Autowired
    VectorStore vectorStore;

    @Value("classpath:redis.txt")
    Resource resource;

    @GetMapping("/load")
    public String load() throws IOException, InterruptedException {
        List<Document> documents = Files.lines(resource.getFile().toPath())
                .map(line -> new Document(line, Map.of("meta1", "meta2")))
                .toList();

        TextSplitter textSplitter = new TokenTextSplitter();

        for (Document document : documents) {
            List<Document> splitteddocs = textSplitter.split(document);
            System.out.println("before adding document: " + document.getFormattedContent());
            vectorStore.add(splitteddocs);
            System.out.println("Added document: " + document.getFormattedContent());
            Thread.sleep(6000);
        }
        return "Loaded " + resource.getFilename();
    }

    @GetMapping("/search")
    public String search() throws IOException, InterruptedException {

        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder()
                .query("Bike for small kids").topK(2).build());
        return results.toString();
    }
}
