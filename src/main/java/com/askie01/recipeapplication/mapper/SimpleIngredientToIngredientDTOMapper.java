package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleIngredientToIngredientDTOMapper implements IngredientToIngredientDTOMapper {

    private final LongIdMapper longIdMapper;
    private final StringNameMapper stringNameMapper;
    private final AmountMapper amountMapper;
    private final MeasureUnitToMeasureUnitDTOMapper measureUnitToMeasureUnitDTOMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public IngredientDTO mapToDTO(Ingredient ingredient) {
        final MeasureUnitDTO measureUnitDTO = new MeasureUnitDTO();
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .measureUnitDTO(measureUnitDTO)
                .build();
        map(ingredient, ingredientDTO);
        return ingredientDTO;
    }

    @Override
    public void map(Ingredient ingredient, IngredientDTO ingredientDTO) {
        mapId(ingredient, ingredientDTO);
        mapName(ingredient, ingredientDTO);
        mapAmount(ingredient, ingredientDTO);
        mapMeasureUnitToMeasureUnitDTO(ingredient, ingredientDTO);
        mapVersion(ingredient, ingredientDTO);
    }

    private void mapId(Ingredient ingredient, IngredientDTO ingredientDTO) {
        longIdMapper.map(ingredient, ingredientDTO);
    }

    private void mapName(Ingredient ingredient, IngredientDTO ingredientDTO) {
        stringNameMapper.map(ingredient, ingredientDTO);
    }

    private void mapAmount(Ingredient ingredient, IngredientDTO ingredientDTO) {
        amountMapper.map(ingredient, ingredientDTO);
    }

    private void mapMeasureUnitToMeasureUnitDTO(Ingredient ingredient, IngredientDTO ingredientDTO) {
        final MeasureUnit measureUnit = ingredient.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
        measureUnitToMeasureUnitDTOMapper.map(measureUnit, measureUnitDTO);
    }

    private void mapVersion(Ingredient ingredient, IngredientDTO ingredientDTO) {
        longVersionMapper.map(ingredient, ingredientDTO);
    }
}
