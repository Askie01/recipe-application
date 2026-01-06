package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomIngredientDTOUnsavedEntityTestFactory implements IngredientDTOUnsavedEntityTestFactory {

    private final Faker faker;
    private final MeasureUnitDTOUnsavedEntityTestFactory measureUnitDTOUnsavedEntityTestFactory;

    @Override
    public IngredientDTO createIngredientDTO() {
        final String randomName = getRandomName();
        final Double randomAmount = getRandomAmount();
        final MeasureUnitDTO randomMeasureUnitDTO = getMeasureUnitDTO();
        return IngredientDTO.builder()
                .name(randomName)
                .amount(randomAmount)
                .measureUnitDTO(randomMeasureUnitDTO)
                .build();
    }

    private String getRandomName() {
        return faker.funnyName().name();
    }

    private double getRandomAmount() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private MeasureUnitDTO getMeasureUnitDTO() {
        return measureUnitDTOUnsavedEntityTestFactory.createMeasureUnitDTO();
    }
}
