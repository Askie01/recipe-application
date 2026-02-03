package com.askie01.recipeapplication.integration.aspect;

import com.askie01.recipeapplication.aspect.RecipeRestControllerV2LoggingAspect;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.RecipeRepositoryV2;
import com.askie01.recipeapplication.service.RecipeServiceV2;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@AutoConfigureRestTestClient
@TestPropertySource(properties = "api.recipe.v2.enabled=true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRestControllerV2LoggingAspect integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRestControllerV2LoggingAspectIntegrationTest {

    @MockitoBean
    private RecipeServiceV2 service;

    private RecipeRequestBody source;
    private final RestTestClient restTestClient;
    private final RecipeRepositoryV2 repository;

    @MockitoSpyBean
    private final RecipeRestControllerV2LoggingAspect aspect;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeRequestBody();
    }

    private static RecipeRequestBody getTestRecipeRequestBody() {
        final IngredientRequestBody ingredient = IngredientRequestBody.builder()
                .name("Test name in ingredient request body")
                .amount(10.2)
                .unit("Test unit int ingredient request body")
                .build();
        return RecipeRequestBody.builder()
                .name("Test recipe request body")
                .image(new byte[24])
                .description("Test description in recipe request body")
                .difficulty(Difficulty.MEDIUM)
                .categories(new HashSet<>(Set.of("Test category in recipe request body")))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(10.2)
                .cookingTime(10)
                .instructions("Test instructions in recipe request body")
                .build();
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerCreateRecipe is called with valid request body")
    void aspect_whenRecipeRestControllerCreateRecipeIsCalledWithValidRequestBody_logsTwoStatements() {
        restTestClient.post().uri("/api/v2/recipes")
                .body(source)
                .exchange()
                .expectStatus()
                .isCreated();
        verify(aspect, times(1)).logBeforeCreateRecipe(any());
        verify(aspect, times(1)).logAfterCreateRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerCreateRecipe is called with invalid request body")
    void aspect_whenRecipeRestControllerCreateRecipeIsCalledWithInvalidRequestBody_skipsLogStatementFromAspect() {
        restTestClient.post().uri("/api/v2/recipes")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerCreateRecipe throws an exception")
    void aspect_whenRecipeRestControllerCreateRecipeThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service).createRecipe(any());
        restTestClient.post().uri("/api/v2/recipes")
                .body(source)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeCreateRecipe(any());
        verify(aspect, times(1)).logAndRethrowCreateRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipe is called with valid id")
    void aspect_whenRecipeRestControllerGetRecipeIsCalledWithValidId_logsTwoStatements() {
        when(service.getRecipe(any(Long.class)))
                .thenReturn(Recipe.builder().categories(new HashSet<>()).ingredients(new HashSet<>()).build());
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        restTestClient.get().uri("/api/v2/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetRecipe(any());
        verify(aspect, times(1)).logAfterReturningGetRecipe(any(), any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerGetRecipe is called with not numeric id")
    void aspect_whenRecipeRestControllerGetRecipeIsCalledWithNotNumericId_skipsLogStatementFromAspect() {
        restTestClient.get().uri("/api/v2/recipes/not-numeric")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipe throws an exception")
    void aspect_whenRecipeRestControllerGetRecipeIsCalledWithInvalidId_logsTwoStatements() throws Throwable {
        when(service.getRecipe(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        final Long id = Long.MAX_VALUE;
        restTestClient.get().uri("/api/v2/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isNotFound();
        verify(aspect).logBeforeGetRecipe(any());
        verify(aspect).logAndRethrowGetRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipes is called with valid page number and page size")
    void aspect_whenRecipeRestControllerGetRecipesIsCalledWithValidPageNumberAndPageSize_logsTwoStatements() {
        when(service.getRecipes(any(), any()))
                .thenReturn(List.of(Recipe.builder().categories(new HashSet<>()).ingredients(new HashSet<>()).build()));
        restTestClient.get().uri("/api/v2/recipes?pageNumber=0&pageSize=10")
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeGetRecipes(any());
        verify(aspect, times(1)).logAfterReturningGetRecipes(any(), any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerGetRecipes is called with invalid page number and page size")
    void aspect_whenRecipeRestControllerGetRecipesIsCalledWithInvalidPageNumberAndPageSize_skipsLogStatementFromAspect() {
        restTestClient.get().uri("/api/v2/recipes?pageNumber=not-numeric&pageSize=not-numeric")
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerGetRecipes throws an exception")
    void aspect_whenRecipeRestControllerGetRecipesThrowsAnException_logsTwoStatements() throws Throwable {
        when(service.getRecipes(any(), any()))
                .thenThrow(RuntimeException.class);
        restTestClient.get().uri("/api/v2/recipes?pageNumber=0&pageSize=10")
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeGetRecipes(any());
        verify(aspect, times(1)).logAndRethrowGetRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerSearchRecipes is called with valid query")
    void aspect_whenRecipeRestControllerSearchRecipesIsCalledWithValidText_logsTwoStatements() {
        when(service.searchRecipes(any(), any(), any()))
                .thenReturn(List.of(Recipe.builder().categories(new HashSet<>()).ingredients(new HashSet<>()).build()));
        restTestClient.get().uri("/api/v2/recipes/search?text=Blueberry&pageNumber=0&pageSize=10")
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeSearchRecipes(any());
        verify(aspect, times(1)).logAfterReturningSearchRecipes(any(), any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerSearchRecipes is called with invalid query")
    void aspect_whenRecipeRestControllerSearchRecipesIsCalledWithInvalidText_logsTwoStatements() {
        restTestClient.get().uri("/api/v2/recipes/search?text=invalid-text&pageNumber=0&pageSize=10")
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeSearchRecipes(any());
        verify(aspect, times(1)).logAfterReturningSearchRecipes(any(), any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerSearchRecipes throws an exception")
    void aspect_whenRecipeRestControllerSearchRecipesThrowsAnException_logsTwoStatements() throws Throwable {
        when(service.searchRecipes(any(), any(), any()))
                .thenThrow(RuntimeException.class);
        restTestClient.get().uri("/api/v2/recipes/search?text=Blueberry&pageNumber=0&pageSize=10")
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeSearchRecipes(any());
        verify(aspect, times(1)).logAndRethrowSearchRecipes(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerUpdateRecipe is called with valid id and request body")
    void aspect_whenRecipeRestControllerUpdateRecipeIsCalledWithValidIdAndRequestBody_logsTwoStatements() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        restTestClient.put().uri("/api/v2/recipes/{id}", id)
                .body(source)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeUpdateRecipe(any());
        verify(aspect, times(1)).logAfterUpdateRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerUpdateRecipe is called with invalid id and request body")
    void aspect_whenRecipeRestControllerUpdateRecipeIsCalledWithInvalidIdAndRequestBody_skipsLogStatementFromAspect() {
        doThrow(RecipeNotFoundException.class)
                .when(service).updateRecipe(any(), any());
        final Long id = Long.MAX_VALUE;
        restTestClient.put().uri("/api/v2/recipes/{id}", id)
                .body(new RecipeRequestBody())
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerUpdateRecipe throws an exception")
    void aspect_whenRecipeRestControllerUpdateRecipeThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service).updateRecipe(any(), any());
        restTestClient.put().uri("/api/v2/recipes/{id}", 1L)
                .body(source)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeUpdateRecipe(any());
        verify(aspect, times(1)).logAndRethrowUpdateRecipe(any());
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerDeleteRecipe is called with valid id")
    void aspect_whenRecipeRestControllerDeleteRecipeIsCalledWithValidId_logsTwoStatements() {
        final Long id = repository.findAll().stream().findAny().orElseThrow().getId();
        restTestClient.delete().uri("/api/v2/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isOk();
        verify(aspect, times(1)).logBeforeDeleteRecipe(any());
        verify(aspect, times(1)).logAfterDeleteRecipe(any());
    }

    @Test
    @DisplayName("aspect should skip log statement when recipeRestControllerDeleteRecipe is called with invalid id")
    void aspect_whenRecipeRestControllerDeleteRecipeIsCalledWithNotNumericId_skipsLogStatementFromAspect() {
        final String id = "not-numeric";
        restTestClient.delete().uri("/api/v2/recipes/{id}", id)
                .exchange()
                .expectStatus()
                .isBadRequest();
        verifyNoInteractions(aspect);
    }

    @Test
    @DisplayName("aspect should log two statements when recipeRestControllerDeleteRecipe throws an exception")
    void aspect_whenRecipeRestControllerDeleteRecipeThrowsAnException_logsTwoStatements() throws Throwable {
        doThrow(RuntimeException.class)
                .when(service).deleteRecipe(any());
        restTestClient.delete().uri("/api/v2/recipes/{id}", 1L)
                .exchange()
                .expectStatus()
                .is5xxServerError();
        verify(aspect, times(1)).logBeforeDeleteRecipe(any());
        verify(aspect, times(1)).logAndRethrowDeleteRecipe(any());
    }
}