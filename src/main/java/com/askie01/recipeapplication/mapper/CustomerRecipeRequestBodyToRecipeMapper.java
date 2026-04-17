package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Recipe;

public interface CustomerRecipeRequestBodyToRecipeMapper
        extends Mapper<CustomerRecipeRequestBody, Recipe>,
        ToEntityMapper<CustomerRecipeRequestBody, Recipe> {
}
