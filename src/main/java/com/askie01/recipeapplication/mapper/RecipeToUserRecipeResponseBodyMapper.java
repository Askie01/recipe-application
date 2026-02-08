package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.UserRecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeToUserRecipeResponseBodyMapper
        extends Mapper<Recipe, UserRecipeResponseBody>,
        ToDTOMapper<Recipe, UserRecipeResponseBody> {
}
