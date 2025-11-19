package com.example.springAI.service;

import com.example.springAI.model.Answer;
import com.example.springAI.model.Question;

public interface RecipeGenerator {

    public Answer generateRecipe(Question question);
}
