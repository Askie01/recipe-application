package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.IngredientDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientDTOUnsavedEntityTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomIngredientDTOUnsavedEntityTestFactoryTestConfiguration {

    @Bean
    public IngredientDTOUnsavedEntityTestFactory ingredientDTOUnsavedEntityTestFactory(Faker faker,
                                                                                       MeasureUnitDTOUnsavedEntityTestFactory measureUnitDTOUnsavedEntityTestFactory) {
        return new RandomIngredientDTOUnsavedEntityTestFactory(faker, measureUnitDTOUnsavedEntityTestFactory);
    }
}
