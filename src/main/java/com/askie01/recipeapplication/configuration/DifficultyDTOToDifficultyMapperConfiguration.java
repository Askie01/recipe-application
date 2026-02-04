package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DifficultyDTOToDifficultyMapper;
import com.askie01.recipeapplication.mapper.SimpleDifficultyDTOToDifficultyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DifficultyDTOToDifficultyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.difficultyDTO-to-difficulty-type",
            havingValue = "simple",
            matchIfMissing = true
    )
    public DifficultyDTOToDifficultyMapper simpleDifficultyDTOToDifficultyMapper() {
        return new SimpleDifficultyDTOToDifficultyMapper();
    }
}
