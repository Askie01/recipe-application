package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.factory.MeasureUnitDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOUnsavedEntityTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomMeasureUnitDTOUnsavedEntityTestFactoryTestConfigurationTemplate {

    @Bean
    public MeasureUnitDTOUnsavedEntityTestFactory measureUnitDTOUnsavedEntityTestFactory(Faker faker) {
        return new RandomMeasureUnitDTOUnsavedEntityTestFactory(faker);
    }
}
