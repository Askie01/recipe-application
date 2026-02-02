package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.RecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.repository.RecipeRepositoryV2;
import com.askie01.recipeapplication.service.DefaultRecipeServiceV2;
import com.askie01.recipeapplication.service.RecipeServiceV2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeServiceV2Configuration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.service.recipe-v2",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeServiceV2 defaultRecipeServiceV2(RecipeRepositoryV2 repository,
                                                  RecipeRequestBodyToRecipeMapper mapper) {
        return new DefaultRecipeServiceV2(repository, mapper);
    }
}
