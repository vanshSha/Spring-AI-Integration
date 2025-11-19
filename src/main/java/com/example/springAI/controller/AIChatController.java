package com.example.springAI.controller;

import com.example.springAI.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIChatController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/movieinfo")
    public String movieInfo(@RequestParam(value = "movieName", defaultValue = "The Matrix") String name) {
        return movieService.movieInfo(name);
    }

}
