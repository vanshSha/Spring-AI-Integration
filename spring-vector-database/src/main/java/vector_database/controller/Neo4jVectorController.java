package vector_database.controller;

import com.example.demo.service.Neo4jVectorService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@RestController
public class Neo4jVectorController {

    @Autowired
    Neo4jVectorService neo4jVectorService;

    @GetMapping("/load")
    public void load() {
        neo4jVectorService.load();
    }

    @GetMapping("/search")
    public List<Document> search() {
        return neo4jVectorService.search("Mountains!");
    }
}
/*
http://localhost:7474

CREATE VECTOR INDEX `spring-ai-document-index`
FOR (n:Document)
ON (n.embedding)
OPTIONS {
  indexConfig: {
    `vector.dimensions`: 1536,
    `vector.similarity_function`: 'cosine'
  }
};

SHOW INDEXES;
 */
