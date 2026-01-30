package com.askie01.recipeapplication.unit.service;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.RecipeDTOToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.repository.RecipeRepository;
import com.askie01.recipeapplication.service.DefaultRecipeServiceV1;
import com.askie01.recipeapplication.service.RecipeServiceV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultRecipeServiceV1 unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultRecipeServiceV1UnitTest {

    private RecipeDTO source;
    private RecipeServiceV1 service;

    @Mock
    private RecipeRepository repository;

    @Mock
    private RecipeDTOToRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeDTO();
        this.service = new DefaultRecipeServiceV1(repository, mapper);
    }

    private static RecipeDTO getTestRecipeDTO() {
        final CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("Test categoryDTO")
                .build();
        final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                .name("Test measure unitDTO")
                .build();
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .name("Test ingredientDTO")
                .amount(1.0)
                .measureUnitDTO(measureUnitDTO)
                .build();
        return RecipeDTO.builder()
                .name("Test recipeDTO")
                .description("Test description")
                .categoryDTOs(Set.of(categoryDTO))
                .ingredientDTOs(Set.of(ingredientDTO))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions")
                .build();
    }

    @Test
    @DisplayName("createRecipe method should return saved recipe when recipeDTO is new")
    void createRecipe_whenRecipeDTOIsNew_returnsSavedRecipe() {
        when(mapper.mapToEntity(any(RecipeDTO.class)))
                .thenReturn(new Recipe());
        when(repository.save(any(Recipe.class)))
                .thenReturn(Recipe.builder().id(1L).build());
        final Long recipeId = service.createRecipe(source).getId();
        assertEquals(1L, recipeId);
    }

    @Test
    @DisplayName("createRecipe method should throw IllegalArgumentException when recipe already exists")
    void createRecipe_whenRecipeDTOAlreadyExists_throwsIllegalArgumentException() {
        source.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> service.createRecipe(source));
    }

    @Test
    @DisplayName("createRecipe method should throw NullPointerException when recipeDTO is null")
    void createRecipe_whenRecipeDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.createRecipe(null));
    }

    @Test
    @DisplayName("getRecipe method should return recipe when id exists")
    void getRecipe_whenIdExists_returnsRecipe() {
        when(repository.findById(1L))
                .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
        final Recipe recipe = service.getRecipe(1L);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("getRecipe method should throw RecipeNotFoundException when id does not exist")
    void getRecipe_whenIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        when(repository.findById(id))
                .thenThrow(new RecipeNotFoundException(id));
        assertThrows(RecipeNotFoundException.class, () -> service.getRecipe(id));
    }

    @Test
    @DisplayName("getRecipe method should throw InvalidDataAccessApiUsageException when id is null")
    void getRecipe_whenIdIsNull_throwsInvalidDataAccessApiUsageException() {
        when(repository.findById(isNull()))
                .thenThrow(InvalidDataAccessApiUsageException.class);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.getRecipe(null));
    }

    @Test
    @DisplayName("getRecipes method should return list of recipes")
    void getRecipes_returnsListOfRecipes() {
        when(repository.findAll())
                .thenReturn(List.of(Recipe.builder().id(1L).build()));
        final List<Recipe> recipes = service.getRecipes();
        assertNotNull(recipes);
    }

    @Test
    @DisplayName("updateRecipe method should return updated recipe when recipeDTO exists")
    void updateRecipe_whenRecipeDTOExists_returnsUpdatedRecipe() {
        when(repository.findById(isNull()))
                .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
        when(repository.save(any(Recipe.class)))
                .thenReturn(Recipe.builder().id(1L).build());
        final Long recipeId = service.updateRecipe(source).getId();
        assertEquals(1L, recipeId);
    }

    @Test
    @DisplayName("updateRecipe method should throw InvalidDataAccessApiUsageException when recipeDTO does not exist")
    void updateRecipe_whenRecipeDTODoesNotExist_throwsInvalidDataAccessApiUsageException() {
        when(repository.findById(isNull()))
                .thenThrow(InvalidDataAccessApiUsageException.class);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.updateRecipe(source));
    }

    @Test
    @DisplayName("updateRecipe method should throw NullPointerException when recipeDTO is null")
    void updateRecipe_whenRecipeDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.updateRecipe(null));
    }

    @Test
    @DisplayName("deleteRecipe method should return deleted recipe when id exists")
    void deleteRecipe_whenIdExists_returnsDeletedRecipe() {
        when(repository.findById(any(Long.class)))
                .thenReturn(Optional.of(Recipe.builder().id(1L).build()));
        final Recipe recipe = service.deleteRecipe(1L);
        assertNotNull(recipe);
    }

    @Test
    @DisplayName("deleteRecipe method should throw RecipeNotFoundException when id does not exist")
    void deleteRecipe_whenIdDoesNotExist_throwsRecipeNotFoundException() {
        final Long id = Long.MAX_VALUE;
        when(repository.findById(any(Long.class)))
                .thenThrow(RecipeNotFoundException.class);
        assertThrows(RecipeNotFoundException.class, () -> service.deleteRecipe(id));
    }

    @Test
    @DisplayName("deleteRecipe method should throw InvalidDataAccessApiUsageException when id is null")
    void deleteRecipe_whenIdIsNull_throwsInvalidDataAccessApiUsageException() {
        doThrow(InvalidDataAccessApiUsageException.class)
                .when(repository).findById(isNull());
        assertThrows(InvalidDataAccessApiUsageException.class, () -> service.deleteRecipe(null));
    }
}