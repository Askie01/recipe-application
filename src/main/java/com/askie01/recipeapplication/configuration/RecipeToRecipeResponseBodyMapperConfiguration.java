package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultRecipeToRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToRecipeResponseBodyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeToRecipeResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.recipe-to-recipe-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeToRecipeResponseBodyMapper defaultRecipeToRecipeResponseBodyMapper() {
        return new DefaultRecipeToRecipeResponseBodyMapper();
    }
}
