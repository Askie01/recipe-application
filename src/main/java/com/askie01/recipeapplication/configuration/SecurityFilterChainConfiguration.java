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
    private static final String CUSTOMERS_API_V1 = "/api/v1/customers/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.authorizeHttpRequests(requestConfiguration -> requestConfiguration
                        .requestMatchers(HttpMethod.POST, RECIPE_API_V2).authenticated()
                        .requestMatchers(HttpMethod.PUT, RECIPE_API_V2).authenticated()
                        .requestMatchers(HttpMethod.DELETE, RECIPE_API_V2).authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/customers/details").authenticated()
                        .requestMatchers(HttpMethod.PUT, CUSTOMERS_API_V1).authenticated()
                        .requestMatchers(HttpMethod.DELETE, CUSTOMERS_API_V1).authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .build();
    }
}
