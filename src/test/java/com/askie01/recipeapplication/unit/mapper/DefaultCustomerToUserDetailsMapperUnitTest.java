package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.mapper.CustomerToUserDetailsMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerToUserDetailsMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DefaultCustomerToUserDetailsMapper unit tests")
@EnabledIfSystemProperty(named = "test-type", matches = "unit")
class DefaultCustomerToUserDetailsMapperUnitTest {

    private Customer customer;
    private CustomerToUserDetailsMapper mapper;

    @BeforeEach
    void setUp() {
        this.customer = getTestCustomer();
        this.mapper = new DefaultCustomerToUserDetailsMapper();
    }

    private static Customer getTestCustomer() {
        return Customer.builder()
                .username("admin")
                .password("{noop}admin")
                .firstName("main")
                .lastName("admin")
                .email("main.admin@gmail.com")
                .mobileNumber("987654321")
                .build();
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to target")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        final UserDetails userDetails = mapper.mapToDTO(customer);
        final String sourceUsername = customer.getUsername();
        final String userDetailsUsername = userDetails.getUsername();
        assertEquals(sourceUsername, userDetailsUsername);

        final String sourcePassword = customer.getPassword();
        final String userDetailsPassword = userDetails.getPassword();
        assertEquals(sourcePassword, userDetailsPassword);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}