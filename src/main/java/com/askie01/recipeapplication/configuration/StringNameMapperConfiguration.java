package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.StringNameMapper;
import com.askie01.recipeapplication.mapper.ValidatedStringNameMapper;
import com.askie01.recipeapplication.validator.StringNameValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringNameMapperConfiguration {

    @Bean
    @ConditionalOnProperty(
            name = "component.mapper.name-type",
            havingValue = "validated-string-name",
            matchIfMissing = true
    )
    public StringNameMapper validatedStringNameMapper(StringNameValidator stringNameValidator) {
        return new ValidatedStringNameMapper(stringNameValidator);
    }
}
