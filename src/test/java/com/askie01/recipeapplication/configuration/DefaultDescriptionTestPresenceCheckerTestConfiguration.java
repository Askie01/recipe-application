package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultDescriptionTestPresenceChecker;
import com.askie01.recipeapplication.checker.DescriptionTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultDescriptionTestPresenceCheckerTestConfiguration {

    @Bean
    public DescriptionTestPresenceChecker descriptionTestPresenceChecker() {
        return new DefaultDescriptionTestPresenceChecker();
    }
}
