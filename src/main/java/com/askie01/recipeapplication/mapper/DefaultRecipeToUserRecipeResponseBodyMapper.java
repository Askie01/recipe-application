package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.UserRecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public class DefaultRecipeToUserRecipeResponseBodyMapper implements RecipeToUserRecipeResponseBodyMapper {

    @Override
    public UserRecipeResponseBody mapToDTO(Recipe recipe) {
        return null;
    }

    @Override
    public void map(Recipe recipe, UserRecipeResponseBody userRecipeResponseBody) {

    }
}
