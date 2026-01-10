package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.ServingsMapper;
import com.askie01.recipeapplication.mapper.ValidatedServingsMapper;
import com.askie01.recipeapplication.validator.ServingsValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.servings-type", havingValue = "validated-servings")
public class ValidatedServingsMapperConfiguration {

    @Bean
    public ServingsMapper servingsMapper(ServingsValidator servingsValidator) {
        return new ValidatedServingsMapper(servingsValidator);
    }
}
