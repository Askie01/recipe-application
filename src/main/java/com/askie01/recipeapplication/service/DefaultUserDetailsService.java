package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.mapper.UserToUserDetailsMapper;
import com.askie01.recipeapplication.model.entity.User;
import com.askie01.recipeapplication.repository.UserRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    final UserRepositoryV1 repository;
    final UserToUserDetailsMapper mapper;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        final User user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapper.mapToDTO(user);
    }
}
