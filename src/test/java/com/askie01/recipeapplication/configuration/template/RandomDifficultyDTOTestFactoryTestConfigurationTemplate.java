package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.factory.DifficultyDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomDifficultyDTOTestFactory;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RandomDifficultyDTOTestFactoryTestConfigurationTemplate {

    @Bean
    public DifficultyDTOTestFactory difficultyDTOTestFactory(Faker faker) {
        return new RandomDifficultyDTOTestFactory(faker);
    }
}
