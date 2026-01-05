package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.factory.CategoryDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryDTOTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomCategoryDTOTestFactoryTestConfigurationTemplate {

    @Bean
    public CategoryDTOTestFactory categoryDTOTestFactory(Faker faker) {
        return new RandomCategoryDTOTestFactory(faker);
    }
}
