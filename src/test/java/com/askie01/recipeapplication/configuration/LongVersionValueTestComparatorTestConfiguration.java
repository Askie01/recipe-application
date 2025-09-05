package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.comparator.LongVersionValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class LongVersionValueTestComparatorTestConfiguration {

    @Bean
    public LongVersionTestComparator longVersionTestComparator() {
        return new LongVersionValueTestComparator();
    }
}
