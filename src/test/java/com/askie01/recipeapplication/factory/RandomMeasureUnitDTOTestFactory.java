package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomMeasureUnitDTOTestFactory implements MeasureUnitDTOTestFactory {

    private final Faker faker;

    public MeasureUnitDTO createMeasureUnitDTO() {
        final Long randomId = getRandomLong();
        final String randomName = getRandomName();
        final Long randomVersion = getRandomLong();
        return MeasureUnitDTO.builder()
                .id(randomId)
                .name(randomName)
                .version(randomVersion)
                .build();
    }

    private long getRandomLong() {
        return faker.number().randomNumber(100000, false);
    }

    private String getRandomName() {
        return faker.funnyName().name();
    }
}
