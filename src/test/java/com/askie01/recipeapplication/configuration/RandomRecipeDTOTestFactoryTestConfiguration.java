package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.*;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomRecipeDTOTestFactoryTestConfiguration {

    @Bean
    public RecipeDTOTestFactory recipeDTOTestFactory(Faker faker,
                                                     DifficultyDTOTestFactory difficultyDTOTestFactory,
                                                     CategoryDTOTestFactory categoryDTOTestFactory,
                                                     IngredientDTOTestFactory ingredientDTOTestFactory) {
        return new RandomRecipeDTOTestFactory(
                faker,
                difficultyDTOTestFactory,
                categoryDTOTestFactory,
                ingredientDTOTestFactory
        );
    }
}
