package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DefaultRecipeToCustomerRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToCustomerRecipeResponseBodyMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RecipeToCustomerRecipeResponseBodyMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.recipe-to-customer-recipe-response-body",
            havingValue = "default",
            matchIfMissing = true
    )
    public RecipeToCustomerRecipeResponseBodyMapper defaultRecipeToCustomerRecipeResponseBodyMapper() {
        return new DefaultRecipeToCustomerRecipeResponseBodyMapper();
    }
}
