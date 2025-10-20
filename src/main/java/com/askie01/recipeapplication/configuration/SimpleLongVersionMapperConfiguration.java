package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.mapper.SimpleLongVersionMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.version-type", havingValue = "simple-long-version")
public class SimpleLongVersionMapperConfiguration {

    @Bean
    public LongVersionMapper longVersionMapper() {
        return new SimpleLongVersionMapper();
    }
}
