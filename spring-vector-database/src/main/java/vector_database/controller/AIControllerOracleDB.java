package vector_database.controller;//package com.example.demo.controller;
//
//import com.example.demo.service.OracleVectorService;
//import org.springframework.ai.document.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
////@RestController
//public class AIControllerOracleDB {
//
//    //@Autowired
//    private OracleVectorService service;
//
//    @GetMapping("/load")
//    public String load() {
//        service.loaddata();
//        return "oracle vector documents loaded successfully";
//    }
//
//    @GetMapping("/search")
//    public List<String> search(@RequestParam("query") String query) {
//        List<Document> results = service.search(query);
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
//1 - docker exec -it oracle23ai bash
//2 - sqlplus sys/mlops@FREEPDB1 as sysdba
//
//ALTER SESSION SET CONTAINER = FREEPDB1;
//
//CREATE USER mlops IDENTIFIED BY mlops;
//
//GRANT CONNECT, RESOURCE TO mlops;
//
//GRANT UNLIMITED TABLESPACE TO mlops;
//
//exit
//
//This is only for verification purpose
//SELECT table_name
//FROM user_tables
//WHERE table_name = 'SPRING_AI_VECTORS';
//
//
// */