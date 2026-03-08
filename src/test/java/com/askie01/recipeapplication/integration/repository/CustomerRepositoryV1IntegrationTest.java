package com.askie01.recipeapplication.integration.repository;

import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("CustomerRepositoryV1 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class CustomerRepositoryV1IntegrationTest {

    private final CustomerRepositoryV1 repository;

    @Test
    @DisplayName("findByUsernameIgnoreCase when username exists returns optional with Customer")
    void findByUsernameIgnoreCase_whenUsernameExists_returnsCustomer() {
        final String username = repository.findAll().stream().findAny().orElseThrow().getUsername();
        final boolean customerExists = repository.findByUsernameIgnoreCase(username).isPresent();
        assertTrue(customerExists);
    }

    @Test
    @DisplayName("findByUsernameIgnoreCase when username does not exist returns empty optional")
    void findByUsernameIgnoreCase_whenUsernameDoesNotExist_returnsEmptyOptional() {
        final String username = "Not existing username";
        final boolean customerExists = repository.findByUsernameIgnoreCase(username).isPresent();
        assertFalse(customerExists);
    }

    @Test
    @DisplayName("findCustomer when query is present returns Page of Customers")
    void findCustomer_whenQueryIsPresent_returnsPageOfCustomers() {
        final Pageable pageable = PageRequest.of(0, 10);
        final boolean resultExists = !repository
                .findCustomer("user", pageable)
                .getContent()
                .isEmpty();
        assertTrue(resultExists);
    }

    @Test
    @DisplayName("findCustomer when query is not present returns random Page of Customers")
    void findCustomer_whenQueryIsNotPresent_returnsRandomCustomerPage() {
        final PageRequest pageable = PageRequest.of(0, 10);
        final boolean resultExists = !repository
                .findCustomer("", pageable)
                .getContent()
                .isEmpty();
        assertTrue(resultExists);
    }

    @Test
    @DisplayName("findCustomer when pageable is null returns Page of Customers")
    void findCustomer_whenPageableIsNull_returnsPageOfRecipes() {
        final boolean resultExists = !repository
                .findCustomer("", null)
                .getContent()
                .isEmpty();
        assertTrue(resultExists);
    }

    @Test
    @DisplayName("deleteByUsername when username exists deletes Customer")
    void deleteByUsername_whenUsernameExists_deletesCustomer() {
        final String username = repository
                .findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("There is no username found in the database."))
                .getUsername();
        repository.deleteByUsername(username);
    }

    @Test
    @DisplayName("deleteByUsername when username does not exist does not delete Customer")
    void deleteByUsername_whenUsernameDoesNotExist_doesNotDeleteCustomer() {
        final String username = "I don't exist";
        repository.deleteByUsername(username);
    }
}