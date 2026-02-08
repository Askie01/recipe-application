package com.askie01.recipeapplication.configuration;

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
    public RecipeServiceV3 defaultRecipeServiceV3() {
        return new DefaultRecipeServiceV3();
    }
}
