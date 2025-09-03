package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.factory.DifficultyTestFactory;
import com.askie01.recipeapplication.factory.RandomDifficultyTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomDifficultyTestFactoryTestConfiguration {

    @Bean
    public DifficultyTestFactory difficultyTestFactory(Faker faker) {
        return new RandomDifficultyTestFactory(faker);
    }
}
