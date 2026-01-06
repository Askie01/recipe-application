package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.DescriptionTestComparator;
import com.askie01.recipeapplication.comparator.DescriptionValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DescriptionValueTestComparatorTestConfiguration {

    @Bean
    public DescriptionTestComparator descriptionTestComparator() {
        return new DescriptionValueTestComparator();
    }
}
