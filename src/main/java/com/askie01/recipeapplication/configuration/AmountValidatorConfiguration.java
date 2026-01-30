package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.AmountValidator;
import com.askie01.recipeapplication.validator.PositiveAmountValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmountValidatorConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.validator.amount-type",
            havingValue = "positive-amount",
            matchIfMissing = true
    )
    public AmountValidator positiveAmountValidator() {
        return new PositiveAmountValidator();
    }
}
