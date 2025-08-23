package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.CookingTimeMapper;
import com.askie01.recipeapplication.mapper.ValidatedCookingTimeMapper;
import com.askie01.recipeapplication.validator.CookingTimeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.cooking-time-type", havingValue = "validated-cooking-time")
public class ValidatedCookingTimeMapperConfiguration {

    @Bean
    public CookingTimeMapper cookingTimeMapper(CookingTimeValidator cookingTimeValidator) {
        return new ValidatedCookingTimeMapper(cookingTimeValidator);
    }
}
