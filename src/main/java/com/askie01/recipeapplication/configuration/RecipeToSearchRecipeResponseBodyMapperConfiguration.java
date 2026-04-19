package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultRecipeToSearchRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToSearchRecipeResponseBodyMapper;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeToSearchRecipeResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            value = "component.mapper.recipe-to-search-recipe-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeToSearchRecipeResponseBodyMapper defaultRecipeToSearchRecipeResponseBodyMapper(RecipeRepositoryV3 recipeRepositoryV3) {
        return new DefaultRecipeToSearchRecipeResponseBodyMapper(recipeRepositoryV3);
    }
}
