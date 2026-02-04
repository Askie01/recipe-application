package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.LongVersionValidator;
import com.askie01.recipeapplication.validator.PositiveLongVersionValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LongVersionValidatorConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.validator.version-type",
            havingValue = "positive-long-version",
            matchIfMissing = true
    )
    public LongVersionValidator longVersionValidator() {
        return new PositiveLongVersionValidator();
    }
}
