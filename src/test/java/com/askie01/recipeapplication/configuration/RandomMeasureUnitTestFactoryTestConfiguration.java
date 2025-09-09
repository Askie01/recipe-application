package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomMeasureUnitTestFactoryTestConfiguration {

    @Bean
    public MeasureUnitTestFactory measureUnitTestFactory(Faker faker) {
        return new RandomMeasureUnitTestFactory(faker);
    }
}
