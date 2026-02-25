package com.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETLJSONReaderService {


    @Value("classpath:bikes_data.json")
    Resource resource;

    public List<Document> loadJSONasDocuemnts() {
        JsonReader jsonReader = new JsonReader(resource,"brand","description");
        return jsonReader.get();
    }
}
