package com.askie01.recipeapplication.integration.aspect;

import com.askie01.recipeapplication.aspect.RecipeRestControllerV1LoggingAspect;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
@SpringBootTest
@AutoConfigureRestTestClient
@TestPropertySource(properties = "api.recipe.v1.enabled=true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRestControllerLoggingAspect integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRestControllerV1LoggingAspectIntegrationTest {

    private RecipeDTO source;
    private final RestTestClient restTestClient;
    private final RecipeRepository repository;

    @MockitoSpyBean
    private final RecipeRestControllerV1LoggingAspect aspect;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeDTO();
    }

    private static RecipeDTO getTestRecipeDTO() {
        final CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("Test category")
                .build();
        final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                .name("Test measure unit")
                .build();
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("Test ingredient")
                .amount(1.0).measureUnitDTO(measureUnitDTO)
                .build();
        return RecipeDTO.builder()
                .name("Test recipe")
                .description("Test description")
                .difficultyDTO(DifficultyDTO.EASY)
                .categoryDTOs(new HashSet<>(Set.of(categoryDTO)))
                .ingredientDTOs(new HashSet<>(Set.of(ingredientDTO)))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions")
                .build();
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerCreateRecipe is called with valid request body")
    void aspect_whenRecipeRestControllerCreateRecipeIsCalledWithValidRequestBody_logsTwoStatements() {
        restTestClient.post().uri("/api/v1/recipes")
                .body(source)
                .exchange()
                .expectStatus()
                .isCreated();
        verify(aspect, times(1)).logBeforeCreateRecipe(any());
        verify(aspect, times(1)).logAfterReturningCreateRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerCreateRecipe request body is invalid")
    void aspect_whenRecipeRestControllerCreateRecipeIsCalledWithInvalidRequestBody_skipsLogStatementFromAspect() {
        restTestClient.post().uri("/api/v1/recipes")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerCreateRecipe throws an error")
    void aspect_whenRecipeRestControllerCreateRecipeThrowsAnError_logsTwoStatements() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        source.setId(id);
        restTestClient.post().uri("/api/v1/recipes")
                .body(source)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeCreateRecipe(any());
        verify(aspect, times(1)).logAfterThrowingCreateRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipeWithValidId is called with valid id")
    void aspect_whenRecipeRestControllerGetRecipeWithValidIdIsCalled_logsTwoStatements() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        restTestClient.get().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetRecipe(any());
        verify(aspect, times(1)).logAfterReturningGetRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerGetRecipeWithNotNumericId is called with not numeric id")
    void aspect_whenRecipeRestControllerGetRecipeWithNotNumericIdIsCalled_skipsLogStatementFromAspect() {
        restTestClient.get().uri("/api/v1/recipes/not-numeric")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipeWithInvalidIdIsCalled")
    void aspect_whenRecipeRestControllerGetRecipeWithInvalidIdIsCalled_logsTwoStatements() {
        restTestClient.get().uri("/api/v1/recipes/0")
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeGetRecipe(any());
        verify(aspect, times(1)).logAfterThrowingGetRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipes is called")
    void aspect_whenRecipeRestControllerGetRecipesIsCalled_logsTwoStatements() {
        restTestClient.get().uri("/api/v1/recipes")
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetRecipes();
        verify(aspect, times(1)).logAfterReturningGetRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerUpdateRecipe is called with valid request body")
    void aspect_whenRecipeRestControllerUpdateRecipeIsCalledWithValidRequestBody_logsTwoStatements() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        source.setId(id);
        restTestClient.put().uri("/api/v1/recipes")
                .body(source)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeUpdateRecipe(any());
        verify(aspect, times(1)).logAfterReturningUpdateRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerUpdateRecipe is called with invalid request body")
    void aspect_whenRecipeRestControllerUpdateRecipeIsCalledWithInvalidRequestBody_skipsLogStatementFromAspect() {
        restTestClient.put().uri("/api/v1/recipes")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerUpdateRecipe is called with new recipe request body")
    void aspect_whenRecipeRestControllerUpdateRecipeIsCalledWithNewRecipeRequestBody_logsTwoStatements() {
        restTestClient.put().uri("/api/v1/recipes")
                .body(source)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeUpdateRecipe(any());
        verify(aspect, times(1)).logAfterThrowingUpdateRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerDeleteRecipe is called with valid id")
    void aspect_whenRecipeRestControllerDeleteRecipeIsCalledWithValidId_logsTwoStatements() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        restTestClient.delete().uri("/api/v1/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeDeleteRecipe(any());
        verify(aspect, times(1)).logAfterReturningDeleteRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerDeleteRecipe is called with not numeric id")
    void aspect_whenRecipeRestControllerDeleteRecipeIsCalledWithNotNumericId_skipsLogStatementFromAspect() {
        restTestClient.delete().uri("/api/v1/recipes/not-numeric")
                .exchange()
                .expectStatus()
                .is4xxClientError();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerDeleteRecipe is called with invalid id")
    void aspect_whenRecipeRestControllerDeleteRecipeIsCalledWithInvalidId_logsTwoStatements() {
        restTestClient.delete().uri("/api/v1/recipes/0")
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect, times(1)).logBeforeDeleteRecipe(any());
        verify(aspect, times(1)).logAfterThrowingDeleteRecipe(any());
    }
}