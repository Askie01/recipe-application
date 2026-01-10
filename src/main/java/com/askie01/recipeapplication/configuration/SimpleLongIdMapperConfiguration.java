package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.LongIdMapper;
import com.askie01.recipeapplication.mapper.SimpleLongIdMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.id-type", havingValue = "simple-long-id")
public class SimpleLongIdMapperConfiguration {

    @Bean
    public LongIdMapper longIdMapper() {
        return new SimpleLongIdMapper();
    }
}
