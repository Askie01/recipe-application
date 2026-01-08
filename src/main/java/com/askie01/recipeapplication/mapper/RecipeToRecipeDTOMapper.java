package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeToRecipeDTOMapper extends
        Mapper<Recipe, RecipeDTO>,
        ToDTOMapper<Recipe, RecipeDTO> {
}
