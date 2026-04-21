package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import com.askie01.recipeapplication.service.DefaultRecipeServiceV3;
import com.askie01.recipeapplication.service.RecipeServiceV3;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeServiceV3Configuration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.service.recipe-v3",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeServiceV3 defaultRecipeServiceV3(RecipeRepositoryV3 recipeRepository,
                                                  CustomerRepositoryV1 customerRepository,
                                                  CustomerRecipeRequestBodyToRecipeMapper customerRecipeRequestBodyMapper) {
        return new DefaultRecipeServiceV3(recipeRepository, customerRepository, customerRecipeRequestBodyMapper);
    }
}
