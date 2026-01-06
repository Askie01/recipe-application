package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.ImageTestComparator;
import com.askie01.recipeapplication.comparator.ImageValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ImageValueTestComparatorTestConfiguration {

    @Bean
    public ImageTestComparator imageTestComparator() {
        return new ImageValueTestComparator();
    }
}
