package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.model.entity.Ingredient;

public interface IngredientToIngredientDTOMapper
        extends Mapper<Ingredient, IngredientDTO>,
        ToDTOMapper<Ingredient, IngredientDTO> {
}
