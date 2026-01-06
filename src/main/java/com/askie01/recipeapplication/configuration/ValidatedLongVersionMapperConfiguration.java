package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.mapper.ValidatedLongVersionMapper;
import com.askie01.recipeapplication.validator.LongVersionValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.version-type", havingValue = "validated-long-version")
public class ValidatedLongVersionMapperConfiguration {

    @Bean
    public LongVersionMapper longVersionMapper(LongVersionValidator longVersionValidator) {
        return new ValidatedLongVersionMapper(longVersionValidator);
    }
}
