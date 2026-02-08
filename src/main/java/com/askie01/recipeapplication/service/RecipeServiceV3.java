package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Recipe;

import java.util.List;

public interface RecipeServiceV3 {

    void createRecipe(String username, RecipeRequestBody requestBody);

    Recipe getRecipe(Long id);

    List<Recipe> getRecipes(String username, Integer pageNumber, Integer pageSize);

    List<Recipe> searchRecipes(String query, Integer pageNumber, Integer pageSize);

    void updateRecipe(String username, Long id, RecipeRequestBody requestBody);

    void deleteRecipe(String username, Long id);
}
