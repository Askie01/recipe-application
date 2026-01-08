package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DifficultyToDifficultyDTOMapper;
import com.askie01.recipeapplication.mapper.SimpleDifficultyToDifficultyDTOMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.difficulty-to-difficultyDTO-type", havingValue = "simple")
public class SimpleDifficultyToDifficultyDTOMapperConfiguration {

    @Bean
    public DifficultyToDifficultyDTOMapper difficultyToDifficultyDTOMapper() {
        return new SimpleDifficultyToDifficultyDTOMapper();
    }
}
