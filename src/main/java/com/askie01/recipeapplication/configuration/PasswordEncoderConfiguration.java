package com.askie01.recipeapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {

    @Bean
    @Primary
    public PasswordEncoder delegatingPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
