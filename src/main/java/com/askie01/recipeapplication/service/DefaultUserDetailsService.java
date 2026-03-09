package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.mapper.CustomerToUserDetailsMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    final CustomerRepositoryV1 repository;
    final CustomerToUserDetailsMapper mapper;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(@NonNull String username) {
        final Customer customer = repository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' not found"));
        return mapper.mapToDTO(customer);
    }
}
