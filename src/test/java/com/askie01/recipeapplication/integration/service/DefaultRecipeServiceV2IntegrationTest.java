package com.askie01.recipeapplication.integration.service;

import com.askie01.recipeapplication.configuration.JpaAuditingConfiguration;
import com.askie01.recipeapplication.configuration.RecipeRequestBodyToRecipeMapperConfiguration;
import com.askie01.recipeapplication.configuration.RecipeServiceV2Configuration;
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
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@Import(value = {
        RecipeServiceV2Configuration.class,
        RecipeRequestBodyToRecipeMapperConfiguration.class,
        JpaAuditingConfiguration.class
})
@TestPropertySource(properties = {
        "component.service.recipe-v2=default",
        "component.mapper.recipe-request-body-to-recipe-type=default",
        "component.auditor-type=recipe-service-auditor"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeServiceV2 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeServiceV2IntegrationTest {

    private RecipeRequestBody source;
    private final RecipeServiceV2 service;

    @MockitoSpyBean
    private final RecipeRepositoryV2 repository;

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
    @DisplayName("createRecipe method should save recipe when recipeRequestBody is present")
    void createRecipe_whenRecipeRequestBodyIsPresent_savesRecipe() {
        service.createRecipe(source);
        verify(repository, times(1)).save(any(Recipe.class));
    }

    @Test
    @DisplayName("createRecipe method should throw NullPointerException when recipeRequestBody is null")
    void createRecipe_whenRecipeRequestBodyIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.createRecipe(null));
    }

    @Test
    @DisplayName("getRecipe method should return recipe when id exists")
    void getRecipe_whenIdExists_returnsRecipe() {
        final Recipe recipe = service.getRecipe(1L);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("getRecipe method should throw RecipeNotFoundException when id does not exist")
    void getRecipe_whenIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipe(id));
    }

    @Test
    @DisplayName("getRecipes method should return list of recipes when pageNumber and pageSize are present")
    void getRecipes_whenPageNumberAndPageSizeArePresent_returnsListOfRecipes() {
        final boolean recipesExist = !service.getRecipes(0, 10).isEmpty();
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("getRecipes method should throw NullPointerException when pageNumber is null")
    void getRecipes_whenPageNumberIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.getRecipes(null, 10));
    }

    @Test
    @DisplayName("getRecipes method should throw NullPointerException when pageSize is null")
    void getRecipes_whenPageSizeIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.getRecipes(0, null));
    }

    @Test
    @DisplayName("searchRecipes method should return list of recipes when text is searchable")
    void searchRecipes_whenTextIsSearchable_returnsListOfRecipes() {
        final String text = "Blueberry";
        final boolean recipesExist = !service.searchRecipes(text, 0, 10).isEmpty();
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("searchRecipes method should return empty list when text is not searchable")
    void searchRecipes_whenTextIsNotSearchable_returnsEmptyList() {
        final String text = "Not searchable text";
        final List<Recipe> recipes = service.searchRecipes(text, 0, 10);
        assertTrue(recipes.isEmpty());
    }

    @Test
    @DisplayName("updateRecipe method should update recipe when recipeRequestBody is present")
    void updateRecipe_whenRecipeRequestBodyIsPresent_updatesRecipe() {
        service.updateRecipe(1L, source);
        verify(repository, times(1)).save(any(Recipe.class));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when recipeRequestBody is null")
    void updateRecipe_whenRecipeRequestBodyIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.updateRecipe(1L, null));
    }

    @Test
    @DisplayName("updateRecipe method should throw RecipeNotFoundException when id does not exist")
    void updateRecipe_whenIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        assertThrows(RecipeNotFoundException.class, () -> service.updateRecipe(id, source));
    }

    @Test
    @DisplayName("updateRecipe method should throw InvalidDataAccessApiUsageException when id is null")
    void updateRecipe_whenIdIsNull_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.updateRecipe(null, source));
    }

    @Test
    @DisplayName("deleteRecipe method should delete recipe when id exists")
    void deleteRecipe_whenIdExists_deletesRecipe() {
        service.deleteRecipe(1L);
        verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("deleteRecipe method should not delete recipe when id does not exist")
    void deleteRecipe_whenIdDoesNotExist_doesNotDeleteRecipe() {
        final Long id = Long.MAX_VALUE;
        service.deleteRecipe(id);
        verify(repository, times(1)).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("deleteRecipe method should throw InvalidDataAccessApiUsageException when id is null")
    void deleteRecipe_whenIdIsNull_throwsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.deleteRecipe(null));
    }
}