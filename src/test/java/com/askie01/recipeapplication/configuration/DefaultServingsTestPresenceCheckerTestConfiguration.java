package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultServingsTestPresenceChecker;
import com.askie01.recipeapplication.checker.ServingsTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultServingsTestPresenceCheckerTestConfiguration {

    @Bean
    public ServingsTestPresenceChecker servingsTestPresenceChecker() {
        return new DefaultServingsTestPresenceChecker();
    }
}
