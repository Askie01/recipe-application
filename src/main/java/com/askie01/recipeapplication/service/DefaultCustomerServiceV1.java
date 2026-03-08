package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerPasswordRequestBody;
import com.askie01.recipeapplication.mapper.CreateCustomerRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.UpdateCustomerDetailsRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class DefaultCustomerServiceV1 implements CustomerServiceV1 {

    private final CustomerRepositoryV1 repository;
    private final PasswordEncoder passwordEncoder;
    private final CreateCustomerRequestBodyToCustomerMapper createRequestBodyMapper;
    private final UpdateCustomerDetailsRequestBodyToCustomerMapper updateRequestBodyMapper;
    private final ClassPathResource classPathResource = new ClassPathResource("static/default-customer.png");

    @Override
    @SneakyThrows
    public void createCustomer(CreateCustomerRequestBody requestBody) {
        final boolean customerExists = repository
                .findByUsernameIgnoreCase(requestBody.getUsername())
                .isPresent();
        if (customerExists) {
            throw new IllegalArgumentException("Customer with username: '" + requestBody.getUsername() + "' already exists");
        }
        final Customer customer = createRequestBodyMapper.mapToEntity(requestBody);
        final String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        final byte[] defaultImage = classPathResource.getContentAsByteArray();
        customer.setImage(defaultImage);
        repository.save(customer);
    }

    @Override
    public Customer getCustomer(String username) {
        return repository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' does not exist"));
    }

    @Override
    public Page<Customer> findCustomer(String query, Pageable pageable) {
        if (query == null) {
            query = "";
        }
        return repository.findCustomer(query, pageable);
    }

    @Override
    public void updateCustomerDetails(String username, UpdateCustomerDetailsRequestBody requestBody) {
        final Customer customer = getCustomer(username);
        updateRequestBodyMapper.map(requestBody, customer);
        repository.save(customer);
    }

    @Override
    public void updateCustomerPassword(String username, UpdateCustomerPasswordRequestBody requestBody) {
        final Customer customer = getCustomer(username);
        final String oldPassword = requestBody.getOldPassword();
        final String encodedCurrentPassword = customer.getPassword();
        final boolean equalPasswords = passwordEncoder.matches(oldPassword, encodedCurrentPassword);
        if (equalPasswords) {
            final String encodedNewPassword = passwordEncoder.encode(requestBody.getNewPassword());
            customer.setPassword(encodedNewPassword);
            repository.save(customer);
        } else {
            throw new IllegalArgumentException("Old password is not correct");
        }
    }

    @Override
    @SneakyThrows
    public void updateCustomerImage(String username, MultipartFile image) {
        final Customer customer = getCustomer(username);
        final boolean isCorrectFormat = image.getContentType().startsWith("image/");
        if (isCorrectFormat) {
            final byte[] newImage = image.getBytes();
            customer.setImage(newImage);
            repository.save(customer);
        } else {
            throw new IllegalArgumentException("File: '" + image.getOriginalFilename() + "' is not jpeg");
        }
    }

    @Override
    @SneakyThrows
    public void restoreDefaultImage(String username) {
        final Customer customer = getCustomer(username);
        final byte[] image = classPathResource.getContentAsByteArray();
        customer.setImage(image);
        repository.save(customer);
    }

    @Override
    public void deleteCustomer(String username) {
        getCustomer(username);
        repository.deleteByUsername(username);
    }
}
