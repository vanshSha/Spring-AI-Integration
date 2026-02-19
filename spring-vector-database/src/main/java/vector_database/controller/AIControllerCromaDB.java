package vector_database.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

//@RestController
public class AIControllerCromaDB {

    //@Value("classpath:input.txt")
    Resource resource;
    VectorStore vectorStore;

    public AIControllerCromaDB(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    //@GetMapping("/load")
    public String load() throws IOException, InterruptedException {
        List<Document> documents = Files.lines(resource.getFile().toPath()).map(Document::new).toList();
        TextSplitter textSplitter = new TokenTextSplitter();
        for(Document document : documents) {
            List<Document> splitteddocs = textSplitter.split(document);
            System.out.println("before adding document: " + document.getText());
            vectorStore.add(splitteddocs);
            Thread.sleep(6000);
            System.out.println("Added document: " + document.getText());
        }
        return "Loaded " + resource.getFilename();
    }

    //@GetMapping("/search")
    public String search() {
        SearchRequest searchRequest = SearchRequest.builder().query("classic novel about wealth and society").topK(3).build();
        List<Document> results = vectorStore.similaritySearch(searchRequest);
        return results.toString();
    }

}
