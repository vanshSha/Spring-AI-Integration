package vector_database.controller;

import com.example.demo.service.ElasticsearchService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ElasticSearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/load")
    public String loadVectors() {
        elasticsearchService.loadVectors();
        return "Loaded vectors for elastic search";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam(defaultValue = "default query") String query) {
        List<Document> docs = elasticsearchService.searchVectors(query);
        List<String> docContents = docs.stream()
                .map(Document::getText)
                .collect(Collectors.toList());
        return docContents;
    }
}

/*
This  URL Check existing index
http://localhost:9200/_cat/indices?v

I hit this url with my existing index on browser || View Data on browser
http://localhost:9200/spring-ai-document-index/_search?pretty

 */
