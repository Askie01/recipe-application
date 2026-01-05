package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.factory.CategoryDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryDTOUnsavedEntityTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomCategoryDTOUnsavedEntityTestFactoryTestConfigurationTemplate {

    @Bean
    public CategoryDTOUnsavedEntityTestFactory categoryDTOUnsavedEntityTestFactory(Faker faker) {
        return new RandomCategoryDTOUnsavedEntityTestFactory(faker);
    }
}
