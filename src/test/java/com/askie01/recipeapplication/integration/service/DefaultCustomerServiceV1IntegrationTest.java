package com.askie01.recipeapplication.integration.service;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerPasswordRequestBody;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.service.CustomerServiceV1;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import(value = {
        CustomerServiceV1Configuration.class,
        PasswordEncoderConfiguration.class,
        CreateCustomerRequestBodyToCustomerMapperConfiguration.class,
        UpdateCustomerDetailsRequestBodyToCustomerMapperConfiguration.class,
        JpaAuditingConfiguration.class
})
@TestPropertySource(properties = {
        "component.service.customer-v1=default",
        "component.mapper.create-customer-request-body-to-customer=default",
        "component.mapper.update-customer-details-request-body-to-customer=default",
        "component.auditor-type=recipe-service-auditor"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultCustomerServiceV1 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultCustomerServiceV1IntegrationTest {

    private final CustomerServiceV1 service;

    @MockitoSpyBean
    private final CustomerRepositoryV1 repository;
    private CreateCustomerRequestBody createCustomerRequestBody;
    private UpdateCustomerDetailsRequestBody updateCustomerDetailsRequestBody;
    private UpdateCustomerPasswordRequestBody updateCustomerPasswordRequestBody;


    @BeforeEach
    void setUp() {
        this.createCustomerRequestBody = getTestCreateCustomerRequestBody();
        this.updateCustomerDetailsRequestBody = getTestUpdateCustomerDetailsRequestBody();
        this.updateCustomerPasswordRequestBody = getTestUpdateCustomerPasswordRequestBody();
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

    private UpdateCustomerDetailsRequestBody getTestUpdateCustomerDetailsRequestBody() {
        return UpdateCustomerDetailsRequestBody.builder()
                .firstName("Test first name")
                .lastName("Test last name")
                .email("test@example.com")
                .mobileNumber("1234567890")
                .build();
    }

    private UpdateCustomerPasswordRequestBody getTestUpdateCustomerPasswordRequestBody() {
        return UpdateCustomerPasswordRequestBody.builder()
                .oldPassword("{noop}askie01")
                .newPassword("{noop}askie02")
                .build();
    }

    @Test
    @DisplayName("createCustomer method should create a customer when request body is present")
    void createCustomer_whenRequestBodyIsPresent_createsCustomer() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow().concat("1");
        createCustomerRequestBody.setUsername(username);
        service.createCustomer(createCustomerRequestBody);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("createCustomer method should throw NullPointerException when request body is null")
    void createCustomer_whenRequestBodyIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.createCustomer(null));
    }


    @Test
    @DisplayName("getCustomer method should return customer when username exists")
    void getCustomer_whenUsernameExists_returnsCustomer() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        final Customer customer = service.getCustomer(username);
        assertNotNull(customer);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
    }

    @Test
    @DisplayName("getCustomer method should throw UsernameNotFoundException when username does not exist")
    void getCustomer_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.getCustomer("Not a username"));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
    }

    @Test
    @DisplayName("findCustomer method should return list of customers where firstName, lastName or username is similar to query")
    void findCustomer_whenQueryIsPresent_returnsCustomerList() {
        final Page<Customer> customerPage = service.findCustomer("adam", Pageable.ofSize(10));
        assertNotNull(customerPage);
        verify(repository, times(1))
                .findCustomer(
                        any(String.class),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("findCustomer method should return all customers when query is null")
    void findCustomer_whenQueryIsNull_returnsAllCustomers() {
        final Page<Customer> customerPage = service.findCustomer(null, Pageable.ofSize(10));
        assertNotNull(customerPage);
        verify(repository, times(1))
                .findCustomer(
                        any(String.class),
                        any(Pageable.class)
                );
    }

    @Test
    @DisplayName("updateCustomerDetails method should update customer if the username exists and request body is valid")
    void updateCustomerDetails_whenUsernameExistsAndRequestBodyIsValid_updatesCustomer() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        final String email = updateCustomerDetailsRequestBody.getEmail().concat("2");
        updateCustomerDetailsRequestBody.setEmail(email);
        service.updateCustomerDetails(username, updateCustomerDetailsRequestBody);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerDetails method should throw UsernameNotFoundException when username does not exist")
    void updateCustomerDetails_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerDetails("Not a username", updateCustomerDetailsRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should update customer's password if username exists and passwords match")
    void updateCustomerPassword_whenUsernameExistsAndPasswordMatches_updatesCustomerPassword() {
        final String username = repository.findAll()
                .stream()
                .map(Customer::getUsername)
                .filter(u -> u.equals("user"))
                .findFirst()
                .orElseThrow();
        updateCustomerPasswordRequestBody.setOldPassword("user");
        service.updateCustomerPassword(username, updateCustomerPasswordRequestBody);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw UsernameNotFoundException when username does not exist")
    void updateCustomerPassword_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerPassword("Not a username", updateCustomerPasswordRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw IllegalArgumentException when passwords do not match")
    void updateCustomerPassword_whenUsernameExistsAndPasswordsDoNotMatch_throwsIllegalArgumentException() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        assertThrows(IllegalArgumentException.class, () -> service.updateCustomerPassword(username, updateCustomerPasswordRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw UsernameNotFoundException when username is null")
    void updateCustomerPassword_whenUsernameIsNull_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerPassword(null, updateCustomerPasswordRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(isNull());
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw NullPointerException when request body is null")
    void updateCustomerPassword_whenRequestBodyIsNull_throwsNullPointerException() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        assertThrows(NullPointerException.class, () -> service.updateCustomerPassword(username, null));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should update customer's image if username and image exists")
    void updateCustomerImage_whenUsernameAndImageExists_updatesCustomerImage() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        final MultipartFile file = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        service.updateCustomerImage(username, file);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should throw UsernameNotFoundException when username does not exist")
    void updateCustomerImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        final MultipartFile file = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerImage("Not a username", file));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should throw UsernameNotFoundException when username is null")
    void updateCustomerImage_whenUsernameIsNull_throwsUsernameNotFoundException() {
        final MultipartFile file = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerImage(null, file));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(isNull());
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should throw NullPointerException when image is null")
    void updateCustomerImage_whenImageIsNull_throwsNullPointerException() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        assertThrows(NullPointerException.class, () -> service.updateCustomerImage(username, null));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should restore default image if username exists")
    void restoreDefaultImage_whenUsernameExists_restoresDefaultImage() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        service.restoreDefaultImage(username);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw UsernameNotFoundException when username does not exist")
    void restoreDefaultImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.restoreDefaultImage("Not a username"));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw UsernameNotFoundException when username is null")
    void restoreDefaultImage_whenUsernameIsNull_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.restoreDefaultImage(null));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(isNull());
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("deleteCustomer method should delete customer if username exists")
    void deleteCustomer_whenUsernameExists_deletesCustomer() {
        final String username = repository.findAll().stream().map(Customer::getUsername).findFirst().orElseThrow();
        service.deleteCustomer(username);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .deleteByUsername(any(String.class));
    }

    @Test
    @DisplayName("deleteCustomer method should do nothing if username does not exist")
    void deleteCustomer_whenUsernameDoesNotExist_doesNothing() {
        assertThrows(UsernameNotFoundException.class, () -> service.deleteCustomer("Not a username"));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .deleteByUsername(any(String.class));
    }

    @Test
    @DisplayName("deleteCustomer method should throw UsernameNotFoundException when username is null")
    void deleteCustomer_whenUsernameIsNull_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.deleteCustomer(null));
        verify(repository, never())
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .deleteByUsername(any(String.class));
    }
}