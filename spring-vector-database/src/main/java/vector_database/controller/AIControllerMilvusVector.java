package vector_database.controller;//package com.example.demo.controller;
//
//import com.example.demo.service.MilvusVectorService;
//import org.springframework.ai.document.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//public class AIControllerMilvusVector {
//
//    @Autowired
//    private MilvusVectorService service;
//
//    @GetMapping("/load")
//    public String load() {
//        service.loaddata();
//        return "oracle vector documents loaded successfully";
//    }
//
//    @GetMapping("/search")
//    public List<String> search(@RequestParam("query") String query) {
//     /   List<Document> results = service.search(query);
//        List<String> contents = results.stream()
//                .map(Document::getText)
//                .collect(Collectors.toList());
//        return contents;
//    }
//
//}
//
///*
//Some Important Command
//1 UI Url : http://localhost:8000
// */