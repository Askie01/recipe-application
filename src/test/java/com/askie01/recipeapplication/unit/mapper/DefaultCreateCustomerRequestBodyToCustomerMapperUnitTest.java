package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.mapper.CreateCustomerRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.DefaultCreateCustomerRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DefaultCreateCustomerRequestBodyToCustomer unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultCreateCustomerRequestBodyToCustomerMapperUnitTest {

    private CreateCustomerRequestBody source;
    private Customer target;
    private CreateCustomerRequestBodyToCustomerMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestCreateCustomerRequestBody();
        this.target = getTestCustomer();
        this.mapper = new DefaultCreateCustomerRequestBodyToCustomerMapper();
    }

    private CreateCustomerRequestBody getTestCreateCustomerRequestBody() {
        return CreateCustomerRequestBody.builder()
                .username("user")
                .password("{noop}user")
                .firstName("simple")
                .lastName("user")
                .email("simple.user@gmail.com")
                .mobileNumber("123456789")
                .build();
    }

    private Customer getTestCustomer() {
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
    @DisplayName("map method should map all common fields from source to target")
    void map_whenSourceIsValid_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final String sourceUsername = source.getUsername();
        final String targetUsername = target.getUsername();
        assertEquals(sourceUsername, targetUsername);

        final String sourcePassword = source.getPassword();
        final String targetPassword = target.getPassword();
        assertEquals(sourcePassword, targetPassword);

        final String sourceFirstName = source.getFirstName();
        final String targetFirstName = target.getFirstName();
        assertEquals(sourceFirstName, targetFirstName);

        final String sourceLastName = source.getLastName();
        final String targetLastName = target.getLastName();
        assertEquals(sourceLastName, targetLastName);

        final String sourceEmail = source.getEmail();
        final String targetEmail = target.getEmail();
        assertEquals(sourceEmail, targetEmail);

        final String sourceMobileNumber = source.getMobileNumber();
        final String targetMobileNumber = target.getMobileNumber();
        assertEquals(sourceMobileNumber, targetMobileNumber);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new Customer and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewCustomerAndReturnIt() {
        final Customer customer = mapper.mapToEntity(source);
        final String sourceUsername = source.getUsername();
        final String customerUsername = customer.getUsername();
        assertEquals(sourceUsername, customerUsername);

        final String sourcePassword = source.getPassword();
        final String customerPassword = customer.getPassword();
        assertEquals(sourcePassword, customerPassword);

        final String sourceFirstName = source.getFirstName();
        final String customerFirstName = customer.getFirstName();
        assertEquals(sourceFirstName, customerFirstName);

        final String sourceLastName = source.getLastName();
        final String customerLastName = customer.getLastName();
        assertEquals(sourceLastName, customerLastName);

        final String sourceEmail = source.getEmail();
        final String customerEmail = customer.getEmail();
        assertEquals(sourceEmail, customerEmail);

        final String sourceMobileNumber = source.getMobileNumber();
        final String customerMobileNumber = customer.getMobileNumber();
        assertEquals(sourceMobileNumber, customerMobileNumber);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}