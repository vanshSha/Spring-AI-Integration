package vector_database.controller;

import com.example.demo.service.OpenSearchService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

//@RestController
public class OpenSearchController {

    @Autowired
    private OpenSearchService openSearchService;

    @GetMapping("/load")
    public String loadVectors() {
        openSearchService.loadVectors();
        return "Loaded vectors for opensearch";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam(defaultValue = "default query") String query) {
        List<Document> docs = openSearchService.searchVectors(query);
        List<String> docContents = docs.stream()
                .map(Document::getText)
                .collect(Collectors.toList());
        return docContents;
    }
}
/*
This command tell me about OpenSearch
If I disable security = true then use this command
http://localhost:9200

otherwise use this command
http://localhost:9200/-k -u admin:strongPass123

 */
