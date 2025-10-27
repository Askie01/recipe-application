package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomMeasureUnitDTOUnsavedEntityTestFactory implements MeasureUnitDTOUnsavedEntityTestFactory {

    private final Faker faker;

    @Override
    public MeasureUnitDTO createMeasureUnitDTO() {
        final String randomName = getRandomName();
        return MeasureUnitDTO.builder()
                .name(randomName)
                .build();
    }

    private String getRandomName() {
        return faker.funnyName().name();
    }
}
