package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.CategoryTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomCategoryTestFactoryTestConfiguration {

    @Bean
    public CategoryTestFactory categoryTestFactory(Faker faker) {
        return new RandomCategoryTestFactory(faker);
    }
}
