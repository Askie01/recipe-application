package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.comparator.DifficultyDifficultyDTOTestComparator;
import com.askie01.recipeapplication.comparator.DifficultyDifficultyDTOValueTestComparator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DifficultyDifficultyDTOValueTestComparatorTestConfiguration {

    @Bean
    public DifficultyDifficultyDTOTestComparator difficultyDifficultyDTOTestComparator() {
        return new DifficultyDifficultyDTOValueTestComparator();
    }
}
