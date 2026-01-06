package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.LongIdValidator;
import com.askie01.recipeapplication.validator.PositiveLongIdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.validator.id-type", havingValue = "positive-long-id")
public class PositiveLongIdValidatorConfiguration {

    @Bean
    public LongIdValidator longIdValidator() {
        return new PositiveLongIdValidator();
    }
}
