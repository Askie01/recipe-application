package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.*;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomRecipeDTOUnsavedEntityTestFactoryTestConfiguration {

    @Bean
    public RecipeDTOUnsavedEntityTestFactory recipeDTOUnsavedEntityTestFactory(Faker faker,
                                                                               DifficultyDTOTestFactory difficultyDTOTestFactory,
                                                                               CategoryDTOUnsavedEntityTestFactory categoryDTOUnsavedEntityTestFactory,
                                                                               IngredientDTOUnsavedEntityTestFactory ingredientDTOUnsavedEntityTestFactory) {
        return new RandomRecipeDTOUnsavedEntityTestFactory(
                faker,
                difficultyDTOTestFactory,
                categoryDTOUnsavedEntityTestFactory,
                ingredientDTOUnsavedEntityTestFactory);
    }
}
