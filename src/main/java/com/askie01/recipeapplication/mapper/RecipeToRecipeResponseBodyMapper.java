package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.RecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeToRecipeResponseBodyMapper extends
        Mapper<Recipe, RecipeResponseBody>,
        ToDTOMapper<Recipe, RecipeResponseBody> {
}
