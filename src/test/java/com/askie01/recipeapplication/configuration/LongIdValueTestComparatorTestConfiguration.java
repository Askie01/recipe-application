package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongIdValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class LongIdValueTestComparatorTestConfiguration {

    @Bean
    public LongIdTestComparator longIdTestComparator() {
        return new LongIdValueTestComparator();
    }
}
