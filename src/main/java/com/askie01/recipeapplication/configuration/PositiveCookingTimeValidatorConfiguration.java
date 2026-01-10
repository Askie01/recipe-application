package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.CookingTimeValidator;
import com.askie01.recipeapplication.validator.PositiveCookingTimeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.validator.cooking-time-type", havingValue = "positive-cooking-time")
public class PositiveCookingTimeValidatorConfiguration {

    @Bean
    public CookingTimeValidator cookingTimeValidator() {
        return new PositiveCookingTimeValidator();
    }
}
