package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.PositiveServingsValidator;
import com.askie01.recipeapplication.validator.ServingsValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServingsValidatorConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.validator.servings-type",
            havingValue = "positive-servings",
            matchIfMissing = true
    )
    public ServingsValidator positiveServingsValidator() {
        return new PositiveServingsValidator();
    }
}
