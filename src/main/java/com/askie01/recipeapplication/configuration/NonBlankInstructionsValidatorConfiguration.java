package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.InstructionsValidator;
import com.askie01.recipeapplication.validator.NonBlankInstructionsValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.validator.instructions-type", havingValue = "non-blank-instructions")
public class NonBlankInstructionsValidatorConfiguration {

    @Bean
    public InstructionsValidator instructionsValidator() {
        return new NonBlankInstructionsValidator();
    }
}
