package vector_database.controller;

import com.example.demo.service.QdrantVectorService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

//@RestController
public class QdrantController {

    @Autowired
    private QdrantVectorService qdrantVectorService;

    @GetMapping("/loadProducts")
    public void loadProducts() {
        qdrantVectorService.loadProducts();
    }


    @GetMapping("/search")
    public List<String> search(@RequestParam(defaultValue = "default query") String query) {
        List<Document> docs = qdrantVectorService.search(query);
        List<String> docContents = docs.stream()
                .map(Document::getText)
                .collect(Collectors.toList());
        return docContents;
    }
}

// This is use for checking collections
// curl http://localhost:6333/collections