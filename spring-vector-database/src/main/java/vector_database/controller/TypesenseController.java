package vector_database.controller;


import com.example.demo.service.TypesenseVectoreService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

// http://localhost:9090/v1/meta - Health Check GET HIT
//@RestController
public class TypesenseController {

    //@Autowired
    private TypesenseVectoreService typesenseVectoreService;

    @GetMapping("/load")
    public String load() {
        typesenseVectoreService.loadData();
        return "oracle vector documents loaded successfully";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam("query") String query) {
        List<Document> results = typesenseVectoreService.search(query);
        List<String> contents = results.stream()
                .map(Document::getText)
                .collect(Collectors.toList());
        return contents;
    }
}
