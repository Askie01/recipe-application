package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.*;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomRecipeTestFactoryTestConfiguration {

    @Bean
    public RecipeTestFactory recipeTestFactory(Faker faker,
                                               DifficultyTestFactory difficultyTestFactory,
                                               CategoryTestFactory categoryTestFactory,
                                               IngredientTestFactory ingredientTestFactory) {
        return new RandomRecipeTestFactory(
                faker,
                difficultyTestFactory,
                categoryTestFactory,
                ingredientTestFactory
        );
    }
}
