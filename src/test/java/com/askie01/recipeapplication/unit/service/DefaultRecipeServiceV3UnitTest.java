package com.askie01.recipeapplication.unit.service;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.CustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import com.askie01.recipeapplication.service.DefaultRecipeServiceV3;
import com.askie01.recipeapplication.service.RecipeServiceV3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultRecipeServiceV3 unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultRecipeServiceV3UnitTest {

    private RecipeServiceV3 service;
    private CustomerRecipeRequestBody requestBody;

    @Mock
    private RecipeRepositoryV3 recipeRepository;

    @Mock
    private CustomerRepositoryV1 customerRepository;

    @Mock
    private CustomerRecipeRequestBodyToRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        this.requestBody = getTestCustomerRecipeRequestBody();
        this.service = new DefaultRecipeServiceV3(recipeRepository, customerRepository, mapper);
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
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        final Recipe recipe = Recipe.builder()
                .categories(new HashSet<>())
                .ingredients(new HashSet<>())
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        when(mapper.mapToEntity(any(CustomerRecipeRequestBody.class)))
                .thenReturn(recipe);

        service.createRecipe("username", requestBody);
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
        when(customerRepository.findByUsernameIgnoreCase(isNull()))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.createRecipe(null, requestBody));
    }

    @Test
    @DisplayName("createRecipe method should throw NullPointerException when request body is null")
    void createRecipe_whenRequestBodyIsNull_throwsNullPointerException() {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        when(mapper.mapToEntity(isNull()))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> service.createRecipe("username", null));
    }

    @Test
    @DisplayName("getRecipe method should return recipe with given id when recipe with that id exists")
    void getRecipe_whenRecipeIdExists_returnsRecipeWithThatId() {
        when(recipeRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new Recipe()));
        final Recipe recipe = service.getRecipe(1L);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("getRecipe method should throw RecipeNotFoundException when recipe with given id does not exist")
    void getRecipe_whenRecipeIdDoesNotExist_throwsRecipeNotFoundException() {
        when(recipeRepository.findById(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipe(1L));
    }

    @Test
    @DisplayName("getRecipeImage method should return recipe image when recipe with given id exists")
    void getRecipeImage_whenRecipeIdExists_returnsRecipeImage() {
        when(recipeRepository.findRecipeImage(any(Long.class)))
                .thenReturn(Optional.of(new byte[24]));
        final byte[] image = service.getRecipeImage(1L);
        assertNotNull(image);
    }

    @Test
    @DisplayName("getRecipeImage method should throw RecipeNotFoundException when recipe with given id does not exist")
    void getRecipeImage_whenRecipeIdDoesNotExist_throwsRecipeNotFoundException() {
        when(recipeRepository.findRecipeImage(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipeImage(1L));
    }

    @Test
    @DisplayName("getCustomerRecipes method should return recipes page when username exists")
    void getCustomerRecipes_whenUsernameExists_returnsRecipesPage() {
        final Page<Recipe> recipesPage = new PageImpl<>(List.of(new Recipe()));
        when(recipeRepository.findByUsername(any(String.class), any(Pageable.class)))
                .thenReturn(recipesPage);
        final boolean pageIsNotEmpty = service
                .getCustomerRecipes("username", PageRequest.of(0, 10))
                .getTotalElements() != 0;
        assertTrue(pageIsNotEmpty);
    }

    @Test
    @DisplayName("getCustomerRecipes method should return empty recipes page when username does not exist")
    void getCustomerRecipes_whenUsernameDoesNotExist_returnsEmptyRecipesPage() {
        when(recipeRepository.findByUsername(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        final boolean pageIsEmpty = service
                .getCustomerRecipes("username", PageRequest.of(0, 10))
                .getTotalElements() == 0;
        assertTrue(pageIsEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return recipes page when query matches recipes")
    void searchRecipes_whenQueryMatchesRecipes_returnsRecipesPage() {
        final Page<Recipe> recipesPage = new PageImpl<>(List.of(new Recipe()));
        when(recipeRepository.searchRecipes(any(String.class), any(Pageable.class)))
                .thenReturn(recipesPage);
        final boolean pageIsNotEmpty = service
                .searchRecipes("query", PageRequest.of(0, 10))
                .getTotalElements() != 0;
        assertTrue(pageIsNotEmpty);
    }

    @Test
    @DisplayName("searchRecipes method should return empty recipes page when query does not match recipes")
    void searchRecipes_whenQueryDoesNotMatchRecipes_returnsEmptyRecipesPage() {
        when(recipeRepository.searchRecipes(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        final boolean pageIsEmpty = service
                .searchRecipes("query", PageRequest.of(0, 10))
                .getTotalElements() == 0;
        assertTrue(pageIsEmpty);
    }

    @Test
    @DisplayName("updateRecipe method should update recipe when username, recipeId and request body are present")
    void updateRecipe_whenUsername_andRecipeId_andRequestBodyArePresent_updatesRecipe() {
        final Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>(List.of(recipe)))
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        service.updateRecipe("username", 1L, requestBody);
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
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.updateRecipe("username", 1L, requestBody));
    }

    @Test
    @DisplayName("updateRecipe method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void updateRecipe_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        assertThrows(RecipeNotFoundException.class, () -> service.updateRecipe("username", 1L, requestBody));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when request body is null")
    void updateRecipe_whenRequestBodyIsNull_throwsNullPointerException() {
        final Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>(List.of(recipe)))
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        doThrow(NullPointerException.class)
                .when(mapper)
                .map(isNull(), any(Recipe.class));

        assertThrows(NullPointerException.class, () -> service.updateRecipe("username", 1L, null));
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
        final Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>(List.of(recipe)))
                .build();
        final MultipartFile image = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        service.updateImage("username", 1L, image);
        verify(customerRepository, times(1))
                .findByUsernameIgnoreCase(any(String.class));
        verify(recipeRepository, times(1))
                .save(any(Recipe.class));
    }

    @Test
    @DisplayName("updateImage method should throw UsernameNotFoundException when username does not exist")
    void updateImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenThrow(UsernameNotFoundException.class);
        final MultipartFile image = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(UsernameNotFoundException.class, () -> service.updateImage("username", 1L, image));
    }

    @Test
    @DisplayName("updateImage method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void updateImage_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        final MultipartFile image = new MockMultipartFile("Image", "new-image.jpg", "image/jpeg", new byte[20]);
        assertThrows(RecipeNotFoundException.class, () -> service.updateImage("username", 1L, image));
    }

    @Test
    @DisplayName("updateImage method should throw IllegalArgumentException when image is not in correct format")
    void updateImage_whenImageIsIncorrectFormat_throwsIllegalArgumentException() {
        final Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>(List.of(recipe)))
                .build();
        final MultipartFile notImage = new MockMultipartFile("Image", "not-image.txt", "text/plain", new byte[20]);
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        assertThrows(IllegalArgumentException.class, () -> service.updateImage("username", 1L, notImage));
    }

    @Test
    @DisplayName("restoreDefaultImage method should restore default image when username and recipeId are present")
    void restoreDefaultImage_whenUsername_andRecipeIdArePresent_restoresDefaultRecipeImage() {
        final Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>(List.of(recipe)))
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        service.restoreDefaultImage("username", 1L);
        verify(recipeRepository, times(1))
                .save(any(Recipe.class));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw UsernameNotFoundException when username does not exist")
    void restoreDefaultImage_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.restoreDefaultImage("username", 1L));
    }

    @Test
    @DisplayName("restoreDefaultImage method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void restoreDefaultImage_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        assertThrows(RecipeNotFoundException.class, () -> service.restoreDefaultImage("username", 1L));
    }

    @Test
    @DisplayName("deleteRecipe method should delete recipe when username and recipeId are present")
    void deleteRecipe_whenUsername_andRecipeIdArePresent_deletesRecipe() {
        final Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>(List.of(recipe)))
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        service.deleteRecipe("username", 1L);
        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    @DisplayName("deleteRecipe method should throw UsernameNotFoundException when username does not exist")
    void deleteRecipe_whenUsernameDoesNotExist_throwsUsernameNotFoundException() {
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenThrow(UsernameNotFoundException.class);
        assertThrows(UsernameNotFoundException.class, () -> service.deleteRecipe("username", 1L));
    }

    @Test
    @DisplayName("deleteRecipe method should throw RecipeNotFoundException when recipeId does not belong to customer")
    void deleteRecipe_whenRecipeIdDoesNotBelongToCustomer_throwsRecipeNotFoundException() {
        final Customer customer = Customer.builder()
                .recipes(new HashSet<>())
                .build();
        when(customerRepository.findByUsernameIgnoreCase(any(String.class)))
                .thenReturn(Optional.of(customer));
        assertThrows(RecipeNotFoundException.class, () -> service.deleteRecipe("username", 1L));
    }
}