package com.askie01.recipeapplication.integration.service;

import com.askie01.recipeapplication.configuration.CustomerToUserDetailsMapperConfiguration;
import com.askie01.recipeapplication.configuration.JpaAuditingConfiguration;
import com.askie01.recipeapplication.configuration.PasswordEncoderConfiguration;
import com.askie01.recipeapplication.configuration.UserDetailsServiceConfiguration;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import(value = {
        UserDetailsServiceConfiguration.class,
        CustomerToUserDetailsMapperConfiguration.class,
        JpaAuditingConfiguration.class,
        PasswordEncoderConfiguration.class
})
@TestPropertySource(properties = {
        "component.service.user-details=default",
        "component.mapper.customer-to-user-details=default",
        "component.auditor-type=recipe-service-auditor"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultUserDetailsService integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultUserDetailsServiceIntegrationTest {

    private final UserDetailsService service;
    private final CustomerRepositoryV1 repository;

    @Test
    @DisplayName("loadUserByUsername method should return UserDetails when username exists")
    void loadUserByUsername_whenUsernameExists_returnsUserDetails() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        final UserDetails user = service.loadUserByUsername(username);
        assertNotNull(user);
    }

    @Test
    @DisplayName("loadUserByUsername method should throw UsernameNotFoundException when username does not exist")
    void loadUserByUsername_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
    }

    @Test
    @DisplayName("loadUserByUsername method should throw UsernameNotFoundException when username is null")
    void loadUserByUsername_whenUsernameIsNull_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(null));
    }
}