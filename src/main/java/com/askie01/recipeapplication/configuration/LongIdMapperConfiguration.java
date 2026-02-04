package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.mapper.SimpleLongIdMapper;
import com.askie01.recipeapplication.mapper.ValidatedLongIdMapper;
import com.askie01.recipeapplication.validator.LongIdValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LongIdMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.id-type",
            havingValue = "simple-long-id",
            matchIfMissing = true
    )
    public LongIdMapper simpleLongIdMapper() {
        return new SimpleLongIdMapper();
    }

    @Bean
    @ConditionalOnProperty(
            name = "component.mapper.id-type",
            havingValue = "validated-long-id"
    )
    public LongIdMapper validatedLongIdMapper(LongIdValidator longIdValidator) {
        return new ValidatedLongIdMapper(longIdValidator);
    }
}
