package com.askie01.recipeapplication.unit.service;

import com.askie01.recipeapplication.mapper.CustomerToUserDetailsMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.service.DefaultUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultUserDetailsService unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultUserDetailsServiceUnitTest {

    private UserDetailsService service;

    @Mock
    private CustomerRepositoryV1 repository;

    @Mock
    private CustomerToUserDetailsMapper mapper;

    @BeforeEach
    void setUp() {
        this.service = new DefaultUserDetailsService(repository, mapper);
    }

    @Test
    @DisplayName("loadUserByUsername method should return UserDetails when username exists")
    void loadUserByUsername_whenUsernameExists_returnsUserDetails() {
        final UserDetails userDetails = User.builder()
                .username("username")
                .password("password")
                .build();
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(new Customer()));
        when(mapper.mapToDTO(any(Customer.class)))
                .thenReturn(userDetails);
        final UserDetails user = service.loadUserByUsername("username");
        assertNotNull(user);
        verify(repository, times(1)).findByUsernameIgnoreCase(any(String.class));
        verify(mapper, times(1)).mapToDTO(any(Customer.class));
    }

    @Test
    @DisplayName("loadUserByUsername method should throw UsernameNotFoundException when username does not exist")
    void loadUserByUsername_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
        verify(repository, times(1)).findByUsernameIgnoreCase(any(String.class));
        verify(mapper, never()).mapToDTO(any(Customer.class));
    }

    @Test
    @DisplayName("loadUserByUsername method should throw UsernameNotFoundException when username is null")
    void loadUserByUsername_whenUsernameIsNull_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(isNull()))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(null));
        verify(repository, times(1)).findByUsernameIgnoreCase(isNull());
        verify(mapper, never()).mapToDTO(any(Customer.class));
    }
}