package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultRecipeToUserRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToUserRecipeResponseBodyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeToUserRecipeResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.recipe-to-user-recipe-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeToUserRecipeResponseBodyMapper defaultRecipeToUserRecipeResponseBodyMapper() {
        return new DefaultRecipeToUserRecipeResponseBodyMapper();
    }
}
