package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.RecipeDTOToRecipeMapper;
import com.askie01.recipeapplication.repository.RecipeRepository;
import com.askie01.recipeapplication.service.DefaultRecipeServiceV1;
import com.askie01.recipeapplication.service.RecipeServiceV1;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeServiceV1Configuration {

    @Bean
    @ConditionalOnProperty(
            name = "component.service.recipe",
            havingValue = "v1",
            matchIfMissing = true
    )
    public RecipeServiceV1 defaultRecipeServiceV1(RecipeRepository repository,
                                                  RecipeDTOToRecipeMapper mapper) {
        return new DefaultRecipeServiceV1(repository, mapper);
    }
}
