package com.askie01.recipeapplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterChainConfiguration {

    private static final String RECIPE_API_V2 = "/api/v2/recipes/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.authorizeHttpRequests(configuration -> configuration
                        .requestMatchers(HttpMethod.POST, RECIPE_API_V2).authenticated()
                        .requestMatchers(HttpMethod.PUT, RECIPE_API_V2).authenticated()
                        .requestMatchers(HttpMethod.DELETE, RECIPE_API_V2).authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
