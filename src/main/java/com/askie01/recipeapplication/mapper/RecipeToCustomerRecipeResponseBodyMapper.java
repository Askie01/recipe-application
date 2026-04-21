package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerRecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface RecipeToCustomerRecipeResponseBodyMapper
        extends Mapper<Recipe, CustomerRecipeResponseBody>,
        ToDTOMapper<Recipe, CustomerRecipeResponseBody> {
}
