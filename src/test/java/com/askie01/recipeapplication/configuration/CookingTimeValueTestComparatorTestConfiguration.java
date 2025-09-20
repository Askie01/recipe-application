package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.CookingTimeTestComparator;
import com.askie01.recipeapplication.comparator.CookingTimeValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CookingTimeValueTestComparatorTestConfiguration {

    @Bean
    public CookingTimeTestComparator cookingTimeTestComparator() {
        return new CookingTimeValueTestComparator();
    }
}
