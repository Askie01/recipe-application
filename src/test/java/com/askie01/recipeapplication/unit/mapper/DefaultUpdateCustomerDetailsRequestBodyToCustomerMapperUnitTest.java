package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.mapper.DefaultUpdateCustomerDetailsRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.UpdateCustomerDetailsRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DefaultUpdateCustomerDetailsRequestBodyToCustomerMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultUpdateCustomerDetailsRequestBodyToCustomerMapperUnitTest {

    private UpdateCustomerDetailsRequestBody source;
    private Customer target;
    private UpdateCustomerDetailsRequestBodyToCustomerMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestUpdateCustomerDetailsRequestBody();
        this.target = getTestCustomer();
        this.mapper = new DefaultUpdateCustomerDetailsRequestBodyToCustomerMapper();
    }

    private UpdateCustomerDetailsRequestBody getTestUpdateCustomerDetailsRequestBody() {
        return UpdateCustomerDetailsRequestBody.builder()
                .firstName("Test first name")
                .lastName("Test last name")
                .email("test@example.com")
                .mobileNumber("1234567890")
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
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
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