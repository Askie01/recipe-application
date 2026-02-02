package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeRequestBodyToRecipeMapper extends
        Mapper<RecipeRequestBody, Recipe>,
        ToEntityMapper<RecipeRequestBody, Recipe> {
}
