package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Recipe;

import java.util.List;

public interface RecipeServiceV1 {
    Recipe createRecipe(RecipeDTO recipeDTO);

    Recipe getRecipe(Long id);

    List<Recipe> getRecipes();

    Recipe updateRecipe(RecipeDTO recipeDTO);

    Recipe deleteRecipe(Long id);
}
