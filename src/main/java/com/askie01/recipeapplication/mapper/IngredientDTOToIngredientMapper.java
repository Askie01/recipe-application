package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.model.entity.Ingredient;

public interface IngredientDTOToIngredientMapper extends
        Mapper<IngredientDTO, Ingredient>,
        ToEntityMapper<IngredientDTO, Ingredient> {
}
