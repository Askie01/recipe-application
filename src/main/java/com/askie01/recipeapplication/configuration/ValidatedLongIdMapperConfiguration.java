package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.mapper.ValidatedLongIdMapper;
import com.askie01.recipeapplication.validator.LongIdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.id-type", havingValue = "validated-long-id")
public class ValidatedLongIdMapperConfiguration {

    @Bean
    public LongIdMapper longIdMapper(LongIdValidator longIdValidator) {
        return new ValidatedLongIdMapper(longIdValidator);
    }
}
