package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.CustomerToCustomerProfileResponseBodyMapperConfiguration;
import com.askie01.recipeapplication.dto.CustomerProfileResponseBody;
import com.askie01.recipeapplication.mapper.CustomerToCustomerProfileResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = CustomerToCustomerProfileResponseBodyMapperConfiguration.class)
@TestPropertySource(properties = "component.mapper.customer-to-customer-profile-response-body=default")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultCustomerToCustomerProfileResponseBodyMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultCustomerToCustomerProfileResponseBodyMapperIntegrationTest {

    private Customer source;
    private CustomerProfileResponseBody target;
    private final CustomerToCustomerProfileResponseBodyMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestCustomer();
        this.target = getTestCustomerProfileResponseBody();
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

    private static CustomerProfileResponseBody getTestCustomerProfileResponseBody() {
        return CustomerProfileResponseBody.builder()
                .username("Customer username")
                .firstName("Customer first name")
                .lastName("Customer last name")
                .build();
    }

    @Test
    @DisplayName("map method should map all common fields from source to target")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final String sourceUsername = source.getUsername();
        final String targetUsername = target.getUsername();
        assertEquals(sourceUsername, targetUsername);

        final String sourceFirstName = source.getFirstName();
        final String targetFirstName = target.getFirstName();
        assertEquals(sourceFirstName, targetFirstName);

        final String sourceLastName = source.getLastName();
        final String targetLastName = target.getLastName();
        assertEquals(sourceLastName, targetLastName);
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
    @DisplayName("mapToDTO method should map all common fields from source to new CustomerProfileResponseBody and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewCustomerProfileResponseBodyAndReturnIt() {
        final CustomerProfileResponseBody customerProfileResponseBody = mapper.mapToDTO(source);
        final String sourceUsername = source.getUsername();
        final String customerProfileResponseBodyUsername = customerProfileResponseBody.getUsername();
        assertEquals(sourceUsername, customerProfileResponseBodyUsername);

        final String sourceFirstName = source.getFirstName();
        final String customerProfileResponseBodyFirstName = customerProfileResponseBody.getFirstName();
        assertEquals(sourceFirstName, customerProfileResponseBodyFirstName);

        final String sourceLastName = source.getLastName();
        final String customerProfileResponseBodyLastName = customerProfileResponseBody.getLastName();
        assertEquals(sourceLastName, customerProfileResponseBodyLastName);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}