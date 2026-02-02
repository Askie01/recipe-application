package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.mapper.RecipeRequestBodyToRecipeMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeRequestBodyToRecipeMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.recipe-request-body-to-recipe",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeRequestBodyToRecipeMapper defaultRecipeRequestBodyToRecipeMapper() {
        return new DefaultRecipeRequestBodyToRecipeMapper();
    }
}
