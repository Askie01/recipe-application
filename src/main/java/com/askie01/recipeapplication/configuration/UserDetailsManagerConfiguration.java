package com.askie01.recipeapplication.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserDetailsManagerConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(name = "component.manager.user-details",
            havingValue = "in-memory",
            matchIfMissing = true
    )
    public UserDetailsManager inMemoryUserDetailsManager() {
        final UserDetails user = createUser("user", "user", "user");
        final UserDetails admin = createUser("admin", "admin", "User", "Admin");
        return new InMemoryUserDetailsManager(user, admin);
    }

    private UserDetails createUser(String username, String password, String... authoritity) {
        final String encodedPassword = passwordEncoder.encode(password);
        final List<SimpleGrantedAuthority> authorities = Arrays
                .stream(authoritity)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(username, encodedPassword, authorities);
    }
}
