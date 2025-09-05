package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.StringNameTestComparator;
import com.askie01.recipeapplication.comparator.StringNameValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StringNameValueTestComparatorTestConfiguration {

    @Bean
    public StringNameTestComparator stringNameTestComparator() {
        return new StringNameValueTestComparator();
    }
}
