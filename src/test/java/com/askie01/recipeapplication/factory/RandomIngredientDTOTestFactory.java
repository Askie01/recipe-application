package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomIngredientDTOTestFactory implements IngredientDTOTestFactory {

    private final Faker faker;
    private final MeasureUnitDTOTestFactory measureUnitDTOTestFactory;

    public IngredientDTO createIngredientDTO() {
        final Long randomId = getRandomLong();
        final String randomName = getRandomName();
        final Double randomAmount = getRandomDouble();
        final MeasureUnitDTO randomMeasureUnitDTO = getMeasureUnitDTO();
        final Long randomVersion = getRandomLong();
        return IngredientDTO.builder()
                .id(randomId)
                .name(randomName)
                .version(randomVersion)
                .amount(randomAmount)
                .measureUnitDTO(randomMeasureUnitDTO)
                .build();
    }

    private long getRandomLong() {
        return faker.number().randomNumber(100000, false);
    }

    private String getRandomName() {
        return faker.funnyName().name();
    }

    private double getRandomDouble() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private MeasureUnitDTO getMeasureUnitDTO() {
        return measureUnitDTOTestFactory.createMeasureUnitDTO();
    }
}
