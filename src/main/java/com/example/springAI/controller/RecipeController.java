package com.example.springAI.controller;

import com.example.springAI.model.Answer;
import com.example.springAI.model.Question;
import com.example.springAI.service.RecipeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {

    @Autowired
    private RecipeGenerator generator;


    @GetMapping("/generate-recipe")
    public Answer generateRecipe(@RequestParam(value = "question", defaultValue = "What are the ingredients  ")String question, @RequestParam(value = "foodName", defaultValue = "pizza") String foodName) {
        return generator.generateRecipe(new Question(question, foodName));
    }
}
