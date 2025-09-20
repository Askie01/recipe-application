package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleIngredientDTOToIngredientMapper implements IngredientDTOToIngredientMapper {

    private final LongIdMapper longIdMapper;
    private final StringNameMapper stringNameMapper;
    private final AmountMapper amountMapper;
    private final MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public Ingredient mapToEntity(IngredientDTO ingredientDTO) {
        final Ingredient ingredient = Ingredient.builder()
                .measureUnit(new MeasureUnit())
                .build();
        map(ingredientDTO, ingredient);
        return ingredient;
    }

    @Override
    public void map(IngredientDTO ingredientDTO, Ingredient ingredient) {
        mapId(ingredientDTO, ingredient);
        mapName(ingredientDTO, ingredient);
        mapAmount(ingredientDTO, ingredient);
        mapMeasureUnitDTOToMeasureUnit(ingredientDTO, ingredient);
        mapVersion(ingredientDTO, ingredient);
    }

    private void mapId(IngredientDTO ingredientDTO, Ingredient ingredient) {
        longIdMapper.map(ingredientDTO, ingredient);
    }

    private void mapName(IngredientDTO ingredientDTO, Ingredient ingredient) {
        stringNameMapper.map(ingredientDTO, ingredient);
    }

    private void mapAmount(IngredientDTO ingredientDTO, Ingredient ingredient) {
        amountMapper.map(ingredientDTO, ingredient);
    }

    private void mapMeasureUnitDTOToMeasureUnit(IngredientDTO ingredientDTO, Ingredient ingredient) {
        final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
        final MeasureUnit measureUnit = ingredient.getMeasureUnit();
        measureUnitDTOToMeasureUnitMapper.map(measureUnitDTO, measureUnit);
    }

    private void mapVersion(IngredientDTO ingredientDTO, Ingredient ingredient) {
        longVersionMapper.map(ingredientDTO, ingredient);
    }
}
