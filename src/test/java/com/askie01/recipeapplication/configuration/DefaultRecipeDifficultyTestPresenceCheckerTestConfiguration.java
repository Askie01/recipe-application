package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultRecipeDifficultyTestPresenceChecker;
import com.askie01.recipeapplication.checker.RecipeDifficultyTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultRecipeDifficultyTestPresenceCheckerTestConfiguration {

    @Bean
    public RecipeDifficultyTestPresenceChecker recipeDifficultyTestPresenceChecker() {
        return new DefaultRecipeDifficultyTestPresenceChecker();
    }
}
