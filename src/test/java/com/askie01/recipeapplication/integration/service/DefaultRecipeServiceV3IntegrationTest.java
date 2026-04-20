package com.askie01.recipeapplication.integration.service;

import com.askie01.recipeapplication.configuration.CustomerRecipeRequestBodyToRecipeMapperConfiguration;
import com.askie01.recipeapplication.configuration.JpaAuditingConfiguration;
import com.askie01.recipeapplication.configuration.RecipeServiceV3Configuration;
import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.CustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import com.askie01.recipeapplication.service.RecipeServiceV3;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Import(value = {
        RecipeServiceV3Configuration.class,
        CustomerRecipeRequestBodyToRecipeMapperConfiguration.class,
        JpaAuditingConfiguration.class
})
@TestPropertySource(properties = {
        "component.service.recipe-v3=default",
        "component.mapper.customer-recipe-request-body-to-recipe-type=default",
        "component.auditor-type=recipe-service-auditor"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeServiceV3 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeServiceV3IntegrationTest {

    private final RecipeServiceV3 service;

    @MockitoSpyBean
    private final RecipeRepositoryV3 recipeRepository;

    @MockitoSpyBean
    private final CustomerRepositoryV1 customerRepository;

    @MockitoSpyBean
    private final CustomerRecipeRequestBodyToRecipeMapper mapper;
    private CustomerRecipeRequestBody requestBody;

    @BeforeEach
    void setUp() {
        this.requestBody = getTestCustomerRecipeRequestBody();
    }

    private static CustomerRecipeRequestBody getTestCustomerRecipeRequestBody() {
        return CustomerRecipeRequestBody.builder()
                .name("Customer recipe name")
                .description("Customer recipe description")
                .difficulty(Difficulty.EASY)
                .categories(new HashSet<>(Set.of(
                        "First category",
                        "Second category"))
                )
                .ingredients(new HashSet<>(Set.of(
                        new IngredientRequestBody("First ingredient", 10.0d, "First unit"),
                        new IngredientRequestBody("Second ingredient", 20.0d, "Second unit")
                )))
                .servings(10.0d)
                .cookingTime(10)
                .instructions("Customer recipe instructions")
                .build();
    }

    @Test
    @DisplayName("createRecipe method should create recipe when username and request body are present")
    void createRecipe_whenUsername_andRequestBodyArePresent_createsRecipe() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        service.createRecipe(username, requestBody);
        verify(customerRepository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(mapper, times(1))
                .mapToEntity(any(CustomerRecipeRequestBody.class));
        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("createRecipe method should throw UsernameNotFoundException when username is null")
    void createRecipe_whenUsernameIsNull_throwsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> service.createRecipe(null, requestBody));
    }

    @Test
    @DisplayName("createRecipe method should throw NullPointerException when request body is null")
    void createRecipe_whenRequestBodyIsNull_throwsNullPointerException() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        assertThrows(NullPointerException.class, () -> service.createRecipe(username, null));
    }

    @Test
    @DisplayName("getRecipe method should return recipe with given id when recipe with that id exists")
    void getRecipe_whenRecipeIdExists_returnsRecipeWithThatId() {
        final Long id = recipeRepository.findAll().getFirst().getId();
        final Recipe recipe = service.getRecipe(id);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("getRecipe method should throw RecipeNotFoundException when recipe with given id does not exist")
    void getRecipe_whenRecipeIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipe(id));
    }

    @Test
    @DisplayName("getRecipeImage method should return recipe image when recipe with given id exists")
    void getRecipeImage_whenRecipeIdExists_returnsRecipeImage() {
        final Long id = recipeRepository.findAll().getFirst().getId();
        final byte[] image = service.getRecipeImage(id);
        assertNotNull(image);
    }

    @Test
    @DisplayName("getRecipeImage method should throw RecipeNotFoundException when recipe with given id does not exist")
    void getRecipeImage_whenRecipeIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipeImage(id));
    }

    @Test
    @DisplayName("getCustomerRecipes method should return recipes page when username exists")
    void getCustomerRecipes_whenUsernameExists_returnsRecipesPage() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        final boolean pageIsNotEmpty = service
                .getCustomerRecipes(username, PageRequest.of(0, 10))
                .getTotalElements() != 0;
        assertTrue(pageIsNotEmpty);
    }

    @Test
    @DisplayName("getCustomerRecipes method should return empty recipes page when username does not exist")
    void getCustomerRecipes_whenUsernameDoesNotExist_returnsEmptyRecipesPage() {
        final String username = "non-existing-username";
        final boolean pageIsEmpty = service
                .getCustomerRecipes(username, PageRequest.of(0, 10))
                .getTotalElements() == 0;
        assertTrue(pageIsEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return recipes page when query matches recipes")
    void searchRecipes_whenQueryMatchesRecipes_returnsRecipesPage() {
        final String query = recipeRepository.findAll().getFirst().getName();
        final boolean pageIsNotEmpty = service
                .searchRecipes(query, PageRequest.of(0, 10))
                .getTotalElements() != 0;
        assertTrue(pageIsNotEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return empty recipes page when query does not match recipes")
    void searchRecipes_whenQueryDoesNotMatchRecipes_returnsEmptyRecipesPage() {
        final String query = "non-existing-query";
        final boolean pageIsEmpty = service
                .searchRecipes(query, PageRequest.of(0, 10))
                .getTotalElements() == 0;
        assertTrue(pageIsEmpty);
    }

    @Test
    @DisplayName("updateRecipe method should update recipe when username, recipeId and request body are present")
    void updateRecipe_whenUsername_andRecipeId_andRequestBodyArePresent_updatesRecipe() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        service.updateRecipe(username, recipeId, requestBody);
        verify(customerRepository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(mapper, times(1))
                .map(any(CustomerRecipeRequestBody.class), any(Recipe.class));
        verify(recipeRepository, times(1))
                .save(any(Recipe.class));
    }

    @Test
    @DisplayName("updateRecipe method should throw UsernameNotFoundException when username does not exist")
    void updateRecipe_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        final String username = "non-existing-username";
        assertThrows(UsernameNotFoundException.class, () -> service.updateRecipe(username, 1L, requestBody));
    }

    @Test
    @DisplayName("updateRecipe method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void updateRecipe_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        final Long recipeId = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.updateRecipe(username, recipeId, requestBody));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when request body is null")
    void updateRecipe_whenRequestBodyIsNull_throwsNullPointerException() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        assertThrows(NullPointerException.class, () -> service.updateRecipe(username, recipeId, null));
        verify(customerRepository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(mapper, times(1))
                .map(isNull(), any(Recipe.class));
        verify(recipeRepository, never())
                .save(any(Recipe.class));
    }

    @Test
    @DisplayName("updateImage method should update image when username, recipeId and image are present")
    void updateImage_whenUsername_andRecipeId_andImageArePresent_updatesImage() {
        final MultipartFile image = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        service.updateImage(username, recipeId, image);
        verify(customerRepository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(recipeRepository, times(1))
                .save(any(Recipe.class));
    }

    @Test
    @DisplayName("updateImage method should throw UsernameNotFoundException when username does not exist")
    void updateImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        final MultipartFile image = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        final String username = "non-existing-username";
        assertThrows(UsernameNotFoundException.class, () -> service.updateImage(username, 1L, image));
    }

    @Test
    @DisplayName("updateImage method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void updateImage_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        final Long recipeId = Long.MAX_VALUE;
        final MultipartFile image = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(RecipeNotFoundException.class, () -> service.updateImage(username, recipeId, image));
    }

    @Test
    @DisplayName("updateImage method should throw IllegalArgumentException when image is not in correct format")
    void updateImage_whenImageIsIncorrectFormat_throwsIllegalArgumentException() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        final MultipartFile notImage = new MockMultipartFile("Image", "not-image.txt", "text/plain", new byte[20]);
        assertThrows(IllegalArgumentException.class, () -> service.updateImage(username, recipeId, notImage));
    }

    @Test
    @DisplayName("restoreDefaultImage method should restore default image when username and recipeId are present")
    void restoreDefaultImage_whenUsername_andRecipeIdArePresent_restoresDefaultRecipeImage() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        service.restoreDefaultImage(username, recipeId);
        verify(recipeRepository, times(1))
                .save(any(Recipe.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw UsernameNotFoundException when username does not exist")
    void restoreDefaultImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        final String username = "non-existing-username";
        assertThrows(UsernameNotFoundException.class, () -> service.restoreDefaultImage(username, 1L));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void restoreDefaultImage_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        final Long recipeId = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.restoreDefaultImage(username, recipeId));
    }

    @Test
    @DisplayName("deleteRecipe method should delete recipe when username and recipeId are present")
    void deleteRecipe_whenUsername_andRecipeIdArePresent_deletesRecipe() {
        final Customer customer = customerRepository.findAll().getFirst();
        final String username = customer.getUsername();
        final Long recipeId = customer.getRecipes().stream().findFirst().orElseThrow().getId();
        service.deleteRecipe(username, recipeId);
        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("deleteRecipe method should throw UsernameNotFoundException when username does not exist")
    void deleteRecipe_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        final String username = "non-existing-username";
        assertThrows(UsernameNotFoundException.class, () -> service.deleteRecipe(username, 1L));
    }

    @Test
    @DisplayName("deleteRecipe method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void deleteRecipe_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final String username = customerRepository.findAll().getFirst().getUsername();
        final Long recipeId = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.deleteRecipe(username, recipeId));
    }
}