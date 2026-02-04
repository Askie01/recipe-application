package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DifficultyToDifficultyDTOMapper;
import com.askie01.recipeapplication.mapper.SimpleDifficultyToDifficultyDTOMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DifficultyToDifficultyDTOMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.difficulty-to-difficultyDTO-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public DifficultyToDifficultyDTOMapper simpleDifficultyToDifficultyDTOMapper() {
        return new SimpleDifficultyToDifficultyDTOMapper();
    }
}
