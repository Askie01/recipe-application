package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.mapper.UserToUserDetailsMapper;
import com.askie01.recipeapplication.repository.UserRepositoryV1;
import com.askie01.recipeapplication.service.DefaultUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @Primary
    @ConditionalOnProperty(name = "component.service.user-details", havingValue = "default", matchIfMissing = true)
    public UserDetailsService defaultUserDetailsService(UserRepositoryV1 repository,
                                                        UserToUserDetailsMapper mapper) {
        return new DefaultUserDetailsService(repository, mapper);
    }

    @Bean
    @ConditionalOnProperty(name = "component.service.user-details", havingValue = "in-memory")
    public UserDetailsService inMemoryUserDetailsService() {
        final UserDetails user = createUser("user", "user", "user");
        final UserDetails admin = createUser("admin", "admin", "User", "Admin");
        return new InMemoryUserDetailsManager(user, admin);
    }

    private UserDetails createUser(String username, String password, String... authority) {
        final String encodedPassword = passwordEncoder.encode(password);
        final List<SimpleGrantedAuthority> authorities = Arrays
                .stream(authority)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(username, encodedPassword, authorities);
    }
}
