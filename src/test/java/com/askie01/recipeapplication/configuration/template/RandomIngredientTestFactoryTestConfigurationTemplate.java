package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.factory.IngredientTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomIngredientTestFactoryTestConfigurationTemplate {

    @Bean
    public IngredientTestFactory ingredientTestFactory(Faker faker,
                                                       MeasureUnitTestFactory measureUnitTestFactory) {
        return new RandomIngredientTestFactory(faker, measureUnitTestFactory);
    }
}
