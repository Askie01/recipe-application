package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.validator.FiveMegaBytesImageValidator;
import com.askie01.recipeapplication.validator.ImageValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ImageValidatorConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.validator.image-type",
            havingValue = "five-mega-bytes-image",
            matchIfMissing = true
    )
    public ImageValidator fiveMegaBytesImageValidator() {
        return new FiveMegaBytesImageValidator();
    }
}
