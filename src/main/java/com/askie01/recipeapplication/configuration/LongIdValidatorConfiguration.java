package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.LongIdValidator;
import com.askie01.recipeapplication.validator.PositiveLongIdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LongIdValidatorConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.validator.id-type",
            havingValue = "positive-long-id",
            matchIfMissing = true
    )
    public LongIdValidator positiveLongIdValidator() {
        return new PositiveLongIdValidator();
    }
}
