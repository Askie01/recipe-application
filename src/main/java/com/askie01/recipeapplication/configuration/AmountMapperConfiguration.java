package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.AmountMapper;
import com.askie01.recipeapplication.mapper.ValidatedAmountMapper;
import com.askie01.recipeapplication.validator.AmountValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmountMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.amount-type",
            havingValue = "validated-amount",
            matchIfMissing = true
    )
    public AmountMapper validatedAmountMapper(AmountValidator amountValidator) {
        return new ValidatedAmountMapper(amountValidator);
    }
}
