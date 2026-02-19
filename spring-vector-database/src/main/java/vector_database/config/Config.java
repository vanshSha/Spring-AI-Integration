package vector_database.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.mongodb.atlas.MongoDBAtlasVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class Config {

    //    @Bean
//    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Support Java 8 date/time (LocalDate, LocalDateTime, etc.)
        mapper.registerModule(new JavaTimeModule());

        // ISO dates instead of timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

//    @Bean //THis part I will not study
//    public VectorStore mongodbVectorStore(MongoTemplate mongoTemplate, OpenAiApi.EmbeddingModel embeddingModel) {
//        return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel,
//                MongoDBAtlasVectorStore.MongoDBVectorStoreConfig.builder().build(), true);
//    }

}
