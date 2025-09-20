package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.IngredientDTOTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientDTOTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomIngredientDTOTestFactoryTestConfiguration {

    @Bean
    public IngredientDTOTestFactory ingredientDTOTestFactory(Faker faker,
                                                             MeasureUnitDTOTestFactory measureUnitDTOTestFactory) {
        return new RandomIngredientDTOTestFactory(faker, measureUnitDTOTestFactory);
    }
}
