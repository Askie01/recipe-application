package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.checker.DefaultLongIdTestPresenceChecker;
import com.askie01.recipeapplication.checker.LongIdTestPresenceChecker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultLongIdTestPresenceCheckerTestConfiguration {

    @Bean
    public LongIdTestPresenceChecker longIdTestPresenceChecker() {
        return new DefaultLongIdTestPresenceChecker();
    }
}
