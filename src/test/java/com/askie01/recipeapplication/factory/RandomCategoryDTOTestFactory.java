package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomCategoryDTOTestFactory implements CategoryDTOTestFactory {

    private final Faker faker;

    public CategoryDTO createCategoryDTO() {
        final Long randomId = getRandomLong();
        final String randomName = getRandomName();
        final Long randomVersion = getRandomLong();
        return CategoryDTO.builder()
                .id(randomId)
                .name(randomName)
                .version(randomVersion)
                .build();
    }

    private Long getRandomLong() {
        return faker.number().randomNumber(100000, false);
    }

    private String getRandomName() {
        return faker.funnyName().name();
    }
}
