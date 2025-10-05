package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.DescriptionMapper;
import com.askie01.recipeapplication.mapper.ValidatedDescriptionMapper;
import com.askie01.recipeapplication.validator.DescriptionValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.mapper.description-type", havingValue = "validated-description")
public class ValidatedDescriptionMapperConfiguration {

    @Bean
    public DescriptionMapper descriptionMapper(DescriptionValidator descriptionValidator) {
        return new ValidatedDescriptionMapper(descriptionValidator);
    }
}
