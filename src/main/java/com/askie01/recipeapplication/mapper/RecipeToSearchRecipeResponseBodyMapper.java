package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.SearchRecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeToSearchRecipeResponseBodyMapper
        extends Mapper<Recipe, SearchRecipeResponseBody>,
        ToDTOMapper<Recipe, SearchRecipeResponseBody> {
}
