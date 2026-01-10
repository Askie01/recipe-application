package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultInstructionsTestPresenceChecker;
import com.askie01.recipeapplication.checker.InstructionsTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultInstructionsTestPresenceCheckerTestConfiguration {

    @Bean
    public InstructionsTestPresenceChecker instructionsTestPresenceChecker() {
        return new DefaultInstructionsTestPresenceChecker();
    }
}
