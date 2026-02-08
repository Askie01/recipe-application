package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Recipe;

import java.util.List;

public class DefaultRecipeServiceV3 implements RecipeServiceV3 {

    @Override
    public void createRecipe(String username, RecipeRequestBody requestBody) {

    }

    @Override
    public Recipe getRecipe(Long id) {
        return null;
    }

    @Override
    public List<Recipe> getRecipes(String username, Integer pageNumber, Integer pageSize) {
        return List.of();
    }

    @Override
    public List<Recipe> searchRecipes(String query, Integer pageNumber, Integer pageSize) {
        return List.of();
    }

    @Override
    public void updateRecipe(String username, Long id, RecipeRequestBody requestBody) {

    }

    @Override
    public void deleteRecipe(String username, Long id) {

    }
}
