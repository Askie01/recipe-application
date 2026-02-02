package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Recipe;

import java.util.List;

public interface RecipeServiceV2 {
    void createRecipe(RecipeRequestBody requestBody);

    Recipe getRecipe(Long id);

    List<Recipe> getRecipes(Integer pageNumber, Integer pageSize);

    List<Recipe> searchRecipes(String text, Integer pageNumber, Integer pageSize);

    void updateRecipe(Long id, RecipeRequestBody requestBody);

    void deleteRecipe(Long id);
}
