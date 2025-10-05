package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeDTOToRecipeMapper extends
        Mapper<RecipeDTO, Recipe>,
        ToEntityMapper<RecipeDTO, Recipe> {
}
