package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.InstructionsValidator;
import com.askie01.recipeapplication.validator.NonBlankInstructionsValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstructionsValidatorConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.validator.instructions-type",
            havingValue = "non-blank-instructions",
            matchIfMissing = true
    )
    public InstructionsValidator nonBlankInstructionsValidator() {
        return new NonBlankInstructionsValidator();
    }
}
