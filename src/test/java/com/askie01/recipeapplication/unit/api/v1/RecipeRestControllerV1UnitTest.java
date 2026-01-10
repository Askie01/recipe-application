package com.askie01.recipeapplication.unit.api.v1;

import com.askie01.recipeapplication.api.v1.RecipeRestControllerV1;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.mapper.RecipeToRecipeDTOMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.service.RecipeServiceV1;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecipeRestControllerV1 unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RecipeRestControllerV1UnitTest {

    private RecipeDTO source;
    private RecipeRestControllerV1 controller;

    @Mock
    private RecipeServiceV1 service;

    @Mock
    private RecipeToRecipeDTOMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getUnsavedRecipeDTO();
        this.controller = new RecipeRestControllerV1(service, mapper);
    }

    private static RecipeDTO getUnsavedRecipeDTO() {
        final Faker faker = new Faker();
        final DifficultyDTOTestFactory difficultyDTOTestFactory = new RandomDifficultyDTOTestFactory(faker);
        final CategoryDTOUnsavedEntityTestFactory categoryDTOUnsavedEntityTestFactory = new RandomCategoryDTOUnsavedEntityTestFactory(faker);
        final MeasureUnitDTOUnsavedEntityTestFactory measureUnitDTOUnsavedEntityTestFactory = new RandomMeasureUnitDTOUnsavedEntityTestFactory(faker);
        final IngredientDTOUnsavedEntityTestFactory ingredientDTOUnsavedEntityTestFactory = new RandomIngredientDTOUnsavedEntityTestFactory(
                faker,
                measureUnitDTOUnsavedEntityTestFactory
        );
        return new RandomRecipeDTOUnsavedEntityTestFactory(
                faker,
                difficultyDTOTestFactory,
                categoryDTOUnsavedEntityTestFactory,
                ingredientDTOUnsavedEntityTestFactory
        )
                .createRecipeDTO();
    }

    @Test
    @DisplayName("createRecipe method should return saved recipe in DTO format when request body is valid")
    void createRecipe_whenRequestBodyIsValid_returnsSavedRecipeDTO() {
        when(service.createRecipe(any(RecipeDTO.class)))
                .thenAnswer(invocation -> {
                    final Recipe recipe = new Recipe();
                    recipe.setId(1L);
                    return recipe;
                });
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(source);
        final ResponseEntity<RecipeDTO> response = controller.createRecipe(source);
        final HttpStatusCode responseStatus = response.getStatusCode();
        final RecipeDTO responseBody = response.getBody();
        assertEquals(HttpStatus.CREATED, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("createRecipe method should throw IllegalArgumentException when request body is invalid")
    void createRecipe_whenRequestBodyIsInvalid_throwsIllegalArgumentException() {
        when(service.createRecipe(any(RecipeDTO.class)))
                .thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> controller.createRecipe(source));
    }

    @Test
    @DisplayName("createRecipe method should throw NullPointerException when request body is null")
    void createRecipe_whenRequestBodyIsNull_throwsNullPointerException() {
        when(service.createRecipe(null))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> controller.createRecipe(null));
    }

    @Test
    @DisplayName("getRecipe method should return recipe in DTO format when id exists")
    void getRecipe_whenIdExists_returnsRecipeDTOWithThatId() {
        when(service.getRecipe(any(Long.class)))
                .thenReturn(new Recipe());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(new RecipeDTO());
        final ResponseEntity<RecipeDTO> response = controller.getRecipe(1L);
        final HttpStatusCode responseStatus = response.getStatusCode();
        final RecipeDTO responseBody = response.getBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("getRecipe method should throw RecipeNotFoundException when id does not exist")
    void getRecipe_whenIdDoesNotExist_throwRecipeNotFoundException() {
        when(service.getRecipe(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        assertThrows(RecipeNotFoundException.class, () -> controller.getRecipe(1L));
    }

    @Test
    @DisplayName("getRecipe method should throw NumberFormatException when id is not a number")
    void getRecipe_whenIdIsNotNumber_throwsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> controller.getRecipe(Long.valueOf("abc")));
    }

    @Test
    @DisplayName("getRecipes method should return list of RecipeDTO objects.")
    void getRecipes_returnsListOfRecipeDTO() {
        when(service.getRecipes())
                .thenReturn(List.of(new Recipe()));
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(new RecipeDTO());
        final ResponseEntity<List<RecipeDTO>> response = controller.getRecipes();
        final HttpStatusCode responseStatus = response.getStatusCode();
        final List<RecipeDTO> responseBody = response.getBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("updateRecipe method should return updated recipe in DTO format when request body is valid")
    void updateRecipe_whenRequestBodyIsValid_returnsUpdatedRecipeDTO() {
        when(service.updateRecipe(any(RecipeDTO.class)))
                .thenReturn(new Recipe());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(source);
        final ResponseEntity<RecipeDTO> response = controller.updateRecipe(source);
        final HttpStatusCode responseStatus = response.getStatusCode();
        final RecipeDTO responseBody = response.getBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("updateRecipe method should throw RecipeNotFoundException when request body is invalid")
    void updateRecipe_whenRequestBodyIsInvalid_throwsRecipeNotFoundException() {
        when(service.updateRecipe(any(RecipeDTO.class)))
                .thenThrow(RecipeNotFoundException.class);
        assertThrows(RecipeNotFoundException.class, () -> controller.updateRecipe(source));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when request body is null")
    void updateRecipe_whenRequestBodyIsNotPresent_throwsNullPointerException() {
        when(service.updateRecipe(null))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> controller.updateRecipe(null));
    }

    @Test
    @DisplayName("deleteRecipe method should return deleted recipe in DTO format when id is valid")
    void deleteRecipe_whenIdIsValid_returnsDeletedRecipeDTO() {
        when(service.deleteRecipe(any(Long.class)))
                .thenReturn(new Recipe());
        when(mapper.mapToDTO(any(Recipe.class)))
                .thenReturn(source);
        final ResponseEntity<RecipeDTO> response = controller.deleteRecipe(1L);
        final HttpStatusCode responseStatus = response.getStatusCode();
        final RecipeDTO responseBody = response.getBody();
        assertEquals(HttpStatus.OK, responseStatus);
        assertNotNull(responseBody);
    }

    @Test
    @DisplayName("deleteRecipe method should throw RecipeNotFoundException when id is invalid")
    void deleteRecipe_whenIdIsInvalid_throwRecipeNotFoundException() {
        when(service.deleteRecipe(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        assertThrows(RecipeNotFoundException.class, () -> controller.deleteRecipe(1L));
    }

    @Test
    @DisplayName("deleteRecipe method should throw NumberFormatException when id is not a number")
    void deleteRecipe_whenIsIsNotNumber_throwsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> controller.deleteRecipe(Long.valueOf("abc")));
    }
}