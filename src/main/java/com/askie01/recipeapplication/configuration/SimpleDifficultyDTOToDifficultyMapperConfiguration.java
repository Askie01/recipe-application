package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DifficultyDTOToDifficultyMapper;
import com.askie01.recipeapplication.mapper.SimpleDifficultyDTOToDifficultyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.difficultyDTO-to-difficulty-type", havingValue = "simple")
public class SimpleDifficultyDTOToDifficultyMapperConfiguration {

    @Bean
    public DifficultyDTOToDifficultyMapper difficultyDTOToDifficultyMapper() {
        return new SimpleDifficultyDTOToDifficultyMapper();
    }
}
