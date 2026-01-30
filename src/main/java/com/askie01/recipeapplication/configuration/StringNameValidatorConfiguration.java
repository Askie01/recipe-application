package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.NonBlankStringNameValidator;
import com.askie01.recipeapplication.validator.StringNameValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringNameValidatorConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.validator.name-type",
            havingValue = "non-blank-string",
            matchIfMissing = true
    )
    public StringNameValidator nonBlankStringNameValidator() {
        return new NonBlankStringNameValidator();
    }
}
