package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Recipe;

import java.util.List;

public interface RecipeService {
    void createRecipe(RecipeDTO recipeDTO);

    Recipe getRecipe(Long id);

    List<Recipe> getRecipes();

    void updateRecipe(RecipeDTO recipeDTO);

    void deleteRecipe(Long id);
}
