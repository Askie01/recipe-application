package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.ImageMapper;
import com.askie01.recipeapplication.mapper.ValidatedImageMapper;
import com.askie01.recipeapplication.validator.ImageValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ImageMapperConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.mapper.image-type",
            havingValue = "validated-image",
            matchIfMissing = true
    )
    public ImageMapper validatedImageMapper(ImageValidator imageValidator) {
        return new ValidatedImageMapper(imageValidator);
    }
}
