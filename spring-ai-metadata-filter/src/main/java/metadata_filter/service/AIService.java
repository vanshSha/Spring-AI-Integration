package metadata_filter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import metadata_filter.model.Product;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AIService {

    @Autowired
    VectorStore vectorStore;

    @Value("classpath:products.json")
    Resource resource;

    public List<Document> loaddata() {
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> documents = readAndPrintJsonFile();
        // Sleep for 1 second
        // Document represent Text unit for RAG, Embeddings, Vector storage, Semantic search,
        for (Document document : documents) {
            List<Document> splitteddocs = textSplitter.split(document);
            try {
                vectorStore.add(splitteddocs);
                System.out.println("Added document: " + document.getText());
                Thread.sleep(100);
            } catch (
                    InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        System.out.println("Transformed documents: " + documents);
        return documents;
    }

    public List<Document> search(String query) {
        //List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(3));
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        /* FilterExpressionBuilder is used to create metaDataFilter for vector search
        similaritySearch is a method of vector store that finds documents whose meaning is closest to the query
        build() is a method used in the Builder Pattern. - It creates the final object after you set all required values step by step
        */
        // Using FilterExpression
        List<Document> results = vectorStore.similaritySearch(SearchRequest.builder().query(query)
                //.topK(3)
                // .filterExpression(filter.eq("brand", "Apple").build()).build());
                .filterExpression(                                                  // Here I am using topK because of normal expression
                       // "brand in ['Samsung'] or brand in ['Sony']").topK(10).build());
                        "brand in ['Samsung'] && price > 500").topK(10).build());
                       // filter.not(filter.in("brand", "Dell", "Apple", "HP")).build()).topK(10).build());
                       // filter.nin("brand", "Dell","Apple").build()).topK(10).build());
                        //filter.nin("brand", "Apple","HP","Dell").build()).topK(10).build());

        // filter.in("brand", "Apple","HP","Dell").build()).topK(10).build());
                        //filter.and(filter.eq("brand", "Apple"), filter.lt("price", 1200.0)).build()).build());
                // Here I am using multiple filter,   filter.gt, filter.lt

        return results;
    }

    public List<Document> readAndPrintJsonFile() {
        List<Document> documents = new ArrayList<>();
        List<Product> products = getProducts();

        for (Product product : products) {
            Document document = new Document(
                    product.getBrand() + " " + product.getDescription(),
                    Map.of("product_name", product.getProductName(),
                            "brand", product.getBrand(),
                            "price", product.getPrice(),
                            "description", product.getDescription(),
                            "year", product.getYear(),
                            "county", product.getCounty()
                    )
            );
            documents.add(document);
        }
        return documents;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        // getInputStream() : read data from a resource as a stream of bytes.
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            /*
            ObjectMapper is a class from Jackson Library used to convert
            Java Objects and JSON
            treeToValue() converts a JsonNode into a Java POJO after processing or validating JSON dynamically. */
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            for (JsonNode node : jsonNode) {
                Product product = objectMapper.treeToValue(node, Product.class);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
