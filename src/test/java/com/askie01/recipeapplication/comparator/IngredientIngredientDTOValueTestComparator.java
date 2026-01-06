package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IngredientIngredientDTOValueTestComparator implements IngredientIngredientDTOTestComparator {

    private final LongIdTestComparator longIdTestComparator;
    private final StringNameTestComparator stringNameTestComparator;
    private final AmountTestComparator amountTestComparator;
    private final MeasureUnitMeasureUnitDTOTestComparator measureUnitMeasureUnitDTOTestComparator;
    private final LongVersionTestComparator longVersionTestComparator;

    @Override
    public boolean compare(Ingredient ingredient, IngredientDTO ingredientDTO) {
        final boolean haveEqualId = haveEqualId(ingredient, ingredientDTO);
        final boolean haveEqualName = haveEqualName(ingredient, ingredientDTO);
        final boolean haveEqualAmount = haveEqualAmount(ingredient, ingredientDTO);
        final boolean haveEqualMeasureUnit = haveEqualMeasureUnit(ingredient, ingredientDTO);
        final boolean haveEqualVersion = haveEqualVersion(ingredient, ingredientDTO);
        return haveEqualId &&
                haveEqualName &&
                haveEqualAmount &&
                haveEqualMeasureUnit &&
                haveEqualVersion;
    }

    private boolean haveEqualId(Ingredient ingredient, IngredientDTO ingredientDTO) {
        return longIdTestComparator.compare(ingredient, ingredientDTO);
    }

    private boolean haveEqualName(Ingredient ingredient, IngredientDTO ingredientDTO) {
        return stringNameTestComparator.compare(ingredient, ingredientDTO);
    }

    private boolean haveEqualAmount(Ingredient ingredient, IngredientDTO ingredientDTO) {
        return amountTestComparator.compare(ingredient, ingredientDTO);
    }

    private boolean haveEqualMeasureUnit(Ingredient ingredient, IngredientDTO ingredientDTO) {
        final MeasureUnit measureUnit = ingredient.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
        return measureUnitMeasureUnitDTOTestComparator.compare(measureUnit, measureUnitDTO);
    }

    private boolean haveEqualVersion(Ingredient ingredient, IngredientDTO ingredientDTO) {
        return longVersionTestComparator.compare(ingredient, ingredientDTO);
    }
}
