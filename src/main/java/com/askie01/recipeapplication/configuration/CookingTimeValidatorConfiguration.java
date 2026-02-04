package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.CookingTimeValidator;
import com.askie01.recipeapplication.validator.PositiveCookingTimeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CookingTimeValidatorConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.validator.cooking-time-type",
            havingValue = "positive-cooking-time",
            matchIfMissing = true
    )
    public CookingTimeValidator positiveCookingTimeValidator() {
        return new PositiveCookingTimeValidator();
    }
}
