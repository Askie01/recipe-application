package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;

import java.util.HashSet;

public class DefaultCreateCustomerRequestBodyToCustomerMapper implements CreateCustomerRequestBodyToCustomerMapper {

    @Override
    public Customer mapToEntity(CreateCustomerRequestBody requestBody) {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        map(requestBody, customer);
        return customer;
    }

    @Override
    public void map(CreateCustomerRequestBody requestBody, Customer customer) {
        mapUsername(requestBody, customer);
        mapPassword(requestBody, customer);
        mapFirstName(requestBody, customer);
        mapLastName(requestBody, customer);
        mapEmail(requestBody, customer);
        mapMobileNumber(requestBody, customer);
    }

    private void mapUsername(CreateCustomerRequestBody requestBody, Customer customer) {
        final String username = requestBody.getUsername();
        customer.setUsername(username);
    }

    private void mapPassword(CreateCustomerRequestBody requestBody, Customer customer) {
        final String password = requestBody.getPassword();
        customer.setPassword(password);
    }

    private void mapFirstName(CreateCustomerRequestBody requestBody, Customer customer) {
        final String firstName = requestBody.getFirstName();
        customer.setFirstName(firstName);
    }

    private void mapLastName(CreateCustomerRequestBody requestBody, Customer customer) {
        final String lastName = requestBody.getLastName();
        customer.setLastName(lastName);
    }

    private void mapEmail(CreateCustomerRequestBody requestBody, Customer customer) {
        final String email = requestBody.getEmail();
        customer.setEmail(email);
    }

    private void mapMobileNumber(CreateCustomerRequestBody requestBody, Customer customer) {
        final String mobileNumber = requestBody.getMobileNumber();
        customer.setMobileNumber(mobileNumber);
    }
}
