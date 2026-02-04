package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.InstructionsMapper;
import com.askie01.recipeapplication.mapper.ValidatedInstructionsMapper;
import com.askie01.recipeapplication.validator.InstructionsValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class InstructionsMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.instructions-type",
            havingValue = "validated-instructions",
            matchIfMissing = true
    )
    public InstructionsMapper validatedInstructionsMapper(InstructionsValidator instructionsValidator) {
        return new ValidatedInstructionsMapper(instructionsValidator);
    }
}
