package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerProfileResponseBody;
import com.askie01.recipeapplication.model.entity.Customer;

public class DefaultCustomerToCustomerProfileResponseBodyMapper implements CustomerToCustomerProfileResponseBodyMapper {

    @Override
    public CustomerProfileResponseBody mapToDTO(Customer customer) {
        final CustomerProfileResponseBody responseBody = new CustomerProfileResponseBody();
        map(customer, responseBody);
        return responseBody;
    }

    @Override
    public void map(Customer customer, CustomerProfileResponseBody responseBody) {
        mapUsername(customer, responseBody);
        mapFirstName(customer, responseBody);
        mapLastName(customer, responseBody);
    }

    private void mapUsername(Customer customer, CustomerProfileResponseBody responseBody) {
        final String username = customer.getUsername();
        responseBody.setUsername(username);
    }

    private void mapFirstName(Customer customer, CustomerProfileResponseBody responseBody) {
        final String firstName = customer.getFirstName();
        responseBody.setFirstName(firstName);
    }

    private void mapLastName(Customer customer, CustomerProfileResponseBody responseBody) {
        final String lastName = customer.getLastName();
        responseBody.setLastName(lastName);
    }
}
