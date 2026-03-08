package com.askie01.recipeapplication.unit.service;

import com.askie01.recipeapplication.dto.CreateCustomerRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerDetailsRequestBody;
import com.askie01.recipeapplication.dto.UpdateCustomerPasswordRequestBody;
import com.askie01.recipeapplication.mapper.CreateCustomerRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.mapper.UpdateCustomerDetailsRequestBodyToCustomerMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.service.CustomerServiceV1;
import com.askie01.recipeapplication.service.DefaultCustomerServiceV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultCustomerServiceV1 unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultCustomerServiceV1UnitTest {

    private CustomerServiceV1 service;
    private CreateCustomerRequestBody createCustomerRequestBody;
    private UpdateCustomerDetailsRequestBody updateCustomerDetailsRequestBody;
    private UpdateCustomerPasswordRequestBody updateCustomerPasswordRequestBody;

    @Mock
    private CustomerRepositoryV1 repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CreateCustomerRequestBodyToCustomerMapper createRequestBodyMapper;

    @Mock
    private UpdateCustomerDetailsRequestBodyToCustomerMapper updateRequestBodyMapper;

    @BeforeEach
    void setUp() {
        this.createCustomerRequestBody = getTestCreateCustomerRequestBody();
        this.updateCustomerDetailsRequestBody = getTestUpdateCustomerDetailsRequestBody();
        this.updateCustomerPasswordRequestBody = getTestUpdateCustomerPasswordRequestBody();
        this.service = new DefaultCustomerServiceV1(repository, passwordEncoder, createRequestBodyMapper, updateRequestBodyMapper);
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
        final Customer customer = Customer.builder()
                .password("Raw password")
                .build();
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        when(createRequestBodyMapper.mapToEntity(any(CreateCustomerRequestBody.class)))
                .thenReturn(customer);
        when(passwordEncoder.encode(any(String.class)))
                .thenReturn("encodedPassword");

        service.createCustomer(createCustomerRequestBody);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
        verify(createRequestBodyMapper, times(1))
                .mapToEntity(any(CreateCustomerRequestBody.class));
        verify(passwordEncoder, times(1))
                .encode(any(String.class));
    }

    @Test
    @DisplayName("createCustomer method should throw NullPointerException when request body is null")
    void createCustomer_whenRequestBodyIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.createCustomer(null));
    }


    @Test
    @DisplayName("getCustomer method should return customer when username exists")
    void getCustomer_whenUsernameExists_returnsCustomer() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(new Customer()));
        final Customer customer = service.getCustomer("username");
        assertNotNull(customer);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
    }

    @Test
    @DisplayName("getCustomer method should throw UsernameNotFoundException when username does not exist")
    void getCustomer_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.getCustomer("username"));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
    }

    @Test
    @DisplayName("findCustomer method should return list of customers where firstName, lastName or username is similar to query")
    void findCustomer_whenQueryIsPresent_returnsCustomerList() {
        when(repository.findCustomer(
                any(String.class),
                any(Pageable.class))
        ).thenReturn(new PageImpl<>(List.of(new Customer())));
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
        when(repository.findCustomer(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Customer())));
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
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(new Customer()));
        service.updateCustomerDetails("username", updateCustomerDetailsRequestBody);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(updateRequestBodyMapper, times(1))
                .map(any(UpdateCustomerDetailsRequestBody.class), any(Customer.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerDetails method should throw UsernameNotFoundException when username does not exist")
    void updateCustomerDetails_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerDetails("username", updateCustomerDetailsRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(updateRequestBodyMapper, never())
                .mapToEntity(any(UpdateCustomerDetailsRequestBody.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should update customer's password if username exists and passwords match")
    void updateCustomerPassword_whenUsernameExistsAndPasswordMatches_updatesCustomerPassword() {
        final Customer customer = Customer.builder()
                .password("Raw password")
                .build();
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(any(String.class), any(String.class)))
                .thenReturn(true);
        when(passwordEncoder.encode(any(String.class)))
                .thenReturn("encodedPassword");
        service.updateCustomerPassword("username", updateCustomerPasswordRequestBody);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(passwordEncoder, times(1))
                .matches(any(String.class), any(String.class));
        verify(passwordEncoder, times(1))
                .encode(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw UsernameNotFoundException when username does not exist")
    void updateCustomerPassword_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerPassword("username", updateCustomerPasswordRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(passwordEncoder, never())
                .matches(any(String.class), any(String.class));
        verify(passwordEncoder, never())
                .encode(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw IllegalArgumentException when passwords do not match")
    void updateCustomerPassword_whenUsernameExistsAndPasswordsDoNotMatch_throwsIllegalArgumentException() {
        final Customer customer = Customer.builder()
                .password("Raw password")
                .build();
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        when(passwordEncoder.matches(any(String.class), any(String.class)))
                .thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.updateCustomerPassword("username", updateCustomerPasswordRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(passwordEncoder, times(1))
                .matches(any(String.class), any(String.class));
        verify(passwordEncoder, never())
                .encode(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw NullPointerException when username is null")
    void updateCustomerPassword_whenUsernameIsNull_throwsNullPointerException() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> service.updateCustomerPassword("username", updateCustomerPasswordRequestBody));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(passwordEncoder, never())
                .matches(any(String.class), any(String.class));
        verify(passwordEncoder, never())
                .encode(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerPassword method should throw NullPointerException when request body is null")
    void updateCustomerPassword_whenRequestBodyIsNull_throwsNullPointerException() {
        final Customer customer = Customer.builder()
                .password("Raw password")
                .build();
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        assertThrows(NullPointerException.class, () -> service.updateCustomerPassword("username", null));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(passwordEncoder, never())
                .matches(any(String.class), any(String.class));
        verify(passwordEncoder, never())
                .encode(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should update customer's image if username and image exists")
    void updateCustomerImage_whenUsernameAndImageExists_updatesCustomerImage() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(new Customer()));
        final MultipartFile file = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        service.updateCustomerImage("username", file);
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should throw UsernameNotFoundException when username does not exist")
    void updateCustomerImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        final MultipartFile file = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(UsernameNotFoundException.class, () -> service.updateCustomerImage("username", file));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should throw NullPointerException when username is null")
    void updateCustomerImage_whenUsernameIsNull_throwsNullPointerException() {
        when(repository.findByUsernameIgnoreCase(isNull()))
                .thenThrow(NullPointerException.class);
        final MultipartFile file = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(NullPointerException.class, () -> service.updateCustomerImage(null, file));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(isNull());
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("updateCustomerImage method should throw NullPointerException when image is null")
    void updateCustomerImage_whenImageIsNull_throwsNullPointerException() {
        final Customer customer = Customer.builder()
                .password("Raw password")
                .build();
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        assertThrows(NullPointerException.class, () -> service.updateCustomerImage("username", null));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should restore default image if username exists")
    void restoreDefaultImage_whenUsernameExists_restoresDefaultImage() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(new Customer()));
        service.restoreDefaultImage("username");
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw UsernameNotFoundException when username does not exist")
    void restoreDefaultImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.restoreDefaultImage("username"));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw UsernameNotFoundException when username is null")
    void restoreDefaultImage_whenUsernameIsNull_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(isNull()))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.restoreDefaultImage(null));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(isNull());
        verify(repository, never())
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("deleteCustomer method should delete customer if username exists")
    void deleteCustomer_whenUsernameExists_deletesCustomer() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(new Customer()));
        service.deleteCustomer("username");
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, times(1))
                .deleteByUsername(any(String.class));
    }

    @Test
    @DisplayName("deleteCustomer method should do nothing if username does not exist")
    void deleteCustomer_whenUsernameDoesNotExist_doesNothing() {
        when(repository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.deleteCustomer("username"));
        verify(repository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .deleteByUsername(any(String.class));
    }

    @Test
    @DisplayName("deleteCustomer method should throw UsernameNotFoundException when username is null")
    void deleteCustomer_whenUsernameIsNull_throwsUsernameNotFoundException() {
        when(repository.findByUsernameIgnoreCase(isNull()))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.deleteCustomer(null));
        verify(repository, never())
                .findByUsernameIgnoreCase(any(String.class));
        verify(repository, never())
                .deleteByUsername(any(String.class));
    }
}