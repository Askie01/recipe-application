package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.DescriptionValidator;
import com.askie01.recipeapplication.validator.NonBlankDescriptionValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DescriptionValidatorConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.validator.description-type",
            havingValue = "non-blank-description",
            matchIfMissing = true
    )
    public DescriptionValidator nonBlankDescriptionValidator() {
        return new NonBlankDescriptionValidator();
    }
}
