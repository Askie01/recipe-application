package com.askie01.recipeapplication.unit.service;

import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.RecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.RecipeRepositoryV2;
import com.askie01.recipeapplication.service.DefaultRecipeServiceV2;
import com.askie01.recipeapplication.service.RecipeServiceV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultRecipeServiceV2UnitTest {

    private RecipeRequestBody source;
    private RecipeServiceV2 service;

    @Spy
    private RecipeRepositoryV2 repository;

    @Mock
    private RecipeRequestBodyToRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeRequestBody();
        this.service = new DefaultRecipeServiceV2(repository, mapper);
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
        when(mapper.mapToEntity(any(RecipeRequestBody.class)))
                .thenReturn(Recipe.builder().id(1L).build());
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
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
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
        when(repository.findDistinctRecipes(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(Recipe.builder().id(1L).build())));
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
        when(repository.search(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(Recipe.builder().id(1L).build())));
        final String text = "Blueberry";
        final boolean recipesExist = !service.searchRecipes(text, 0, 10).isEmpty();
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("searchRecipes method should return empty list when text is not searchable")
    void searchRecipes_whenTextIsNotSearchable_returnsEmptyList() {
        when(repository.search(any(String.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        final String text = "Not searchable text";
        final List<Recipe> recipes = service.searchRecipes(text, 0, 10);
        assertTrue(recipes.isEmpty());
    }

    @Test
    @DisplayName("updateRecipe method should update recipe when recipeRequestBody is present")
    void updateRecipe_whenRecipeRequestBodyIsPresent_updatesRecipe() {
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
        service.updateRecipe(1L, source);
        verify(repository, times(1)).save(any(Recipe.class));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when recipeRequestBody is null")
    void updateRecipe_whenRecipeRequestBodyIsNull_throwsNullPointerException() {
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
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
        when(repository.findById(isNull()))
                .thenThrow(InvalidDataAccessApiUsageException.class);
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
        doThrow(InvalidDataAccessApiUsageException.class)
                .when(repository).deleteById(isNull());
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.deleteRecipe(null));
    }
}