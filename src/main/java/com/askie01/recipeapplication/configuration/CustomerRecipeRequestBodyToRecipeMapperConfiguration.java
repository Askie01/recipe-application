package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerRecipeRequestBodyToRecipeMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CustomerRecipeRequestBodyToRecipeMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.customer-recipe-request-body-to-recipe",
            havingValue = "default",
            matchIfMissing = true
    )
    public CustomerRecipeRequestBodyToRecipeMapper defaultCustomerRecipeRequestBodyToRecipeMapper() {
        return new DefaultCustomerRecipeRequestBodyToRecipeMapper();
    }
}
