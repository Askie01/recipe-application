package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomMeasureUnitDTOTestFactoryTestConfiguration {

    @Bean
    public MeasureUnitDTOTestFactory measureUnitDTOTestFactory(Faker faker) {
        return new RandomMeasureUnitDTOTestFactory(faker);
    }
}
