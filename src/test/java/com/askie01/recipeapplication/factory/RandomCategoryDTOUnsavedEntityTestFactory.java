package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomCategoryDTOUnsavedEntityTestFactory implements CategoryDTOUnsavedEntityTestFactory {

    private final Faker faker;

    @Override
    public CategoryDTO createCategoryDTO() {
        final String randomName = getRandomName();
        return CategoryDTO.builder()
                .name(randomName)
                .build();
    }

    private String getRandomName() {
        return faker.funnyName().name();
    }
}
