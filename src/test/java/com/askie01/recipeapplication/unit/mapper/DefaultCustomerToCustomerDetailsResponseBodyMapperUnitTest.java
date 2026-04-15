package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.CustomerDetailsResponseBody;
import com.askie01.recipeapplication.mapper.CustomerToCustomerDetailsResponseBodyMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerToCustomerDetailsResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DefaultCustomerToCustomerDetailsResponseBodyMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultCustomerToCustomerDetailsResponseBodyMapperUnitTest {

    private Customer source;
    private CustomerDetailsResponseBody target;
    private CustomerToCustomerDetailsResponseBodyMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestCustomer();
        this.target = getTestCustomerDetailsResponseBody();
        this.mapper = new DefaultCustomerToCustomerDetailsResponseBodyMapper();
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

    private static CustomerDetailsResponseBody getTestCustomerDetailsResponseBody() {
        return CustomerDetailsResponseBody.builder()
                .firstName("Customer first name")
                .lastName("Customer last name")
                .email("customer@example.com")
                .mobileNumber("123456789")
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
    @DisplayName("mapToDTO method should map all common fields from source to new CustomerDetailsResponseBody and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewCustomerDetailsResponseBodyAndReturnIt() {
        final CustomerDetailsResponseBody customerDetailsResponseBody = mapper.mapToDTO(source);
        final String sourceFirstName = source.getFirstName();
        final String customerDetailsResponseBodyFirstName = customerDetailsResponseBody.getFirstName();
        assertEquals(sourceFirstName, customerDetailsResponseBodyFirstName);

        final String sourceLastName = source.getLastName();
        final String customerDetailsResponseBodyLastName = customerDetailsResponseBody.getLastName();
        assertEquals(sourceLastName, customerDetailsResponseBodyLastName);

        final String sourceEmail = source.getEmail();
        final String customerDetailsResponseBodyEmail = customerDetailsResponseBody.getEmail();
        assertEquals(sourceEmail, customerDetailsResponseBodyEmail);

        final String sourceMobileNumber = source.getMobileNumber();
        final String customerDetailsResponseBodyMobileNumber = customerDetailsResponseBody.getMobileNumber();
        assertEquals(sourceMobileNumber, customerDetailsResponseBodyMobileNumber);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}