package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.IngredientResponseBody;
import com.askie01.recipeapplication.dto.SearchRecipeResponseBody;
import com.askie01.recipeapplication.mapper.DefaultRecipeToSearchRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToSearchRecipeResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultRecipeToSearchRecipeResponseBodyMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultRecipeToSearchRecipeResponseBodyMapperUnitTest {

    private Recipe source;
    private SearchRecipeResponseBody target;
    private RecipeToSearchRecipeResponseBodyMapper mapper;

    @Mock
    private RecipeRepositoryV3 repository;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipe();
        this.target = getTestSearchRecipeResponseBody();
        this.mapper = new DefaultRecipeToSearchRecipeResponseBodyMapper(repository);
    }

    private static Recipe getTestRecipe() {
        final Category category = Category.builder()
                .id(2L)
                .name("Test category")
                .version(2L)
                .build();
        final MeasureUnit measureUnit = MeasureUnit.builder()
                .id(2L)
                .name("Test measure unit")
                .version(2L)
                .build();
        final Ingredient ingredient = Ingredient.builder()
                .id(2L)
                .name("Test ingredient")
                .amount(2.0)
                .measureUnit(measureUnit)
                .version(2L)
                .build();
        return Recipe.builder()
                .id(2L)
                .name("Test recipe")
                .image(new byte[48])
                .description("Test description")
                .difficulty(Difficulty.EASY)
                .categories(new HashSet<>(Set.of(category)))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(2.0)
                .cookingTime(20)
                .instructions("Test instructions in Recipe")
                .version(2L)
                .build();
    }

    private static SearchRecipeResponseBody getTestSearchRecipeResponseBody() {
        return SearchRecipeResponseBody.builder()
                .owner("Test owner")
                .id(1L)
                .name("Customer recipe name")
                .description("Customer recipe description")
                .difficulty(Difficulty.EASY)
                .categories(new HashSet<>(Set.of(
                        "First category",
                        "Second category"))
                )
                .ingredients(new HashSet<>(Set.of(
                        new IngredientResponseBody("First ingredient", 10.0d, "First unit"),
                        new IngredientResponseBody("Second ingredient", 20.0d, "Second unit")
                )))
                .servings(10.0d)
                .cookingTime(10)
                .instructions("Customer recipe instructions")
                .build();
    }

    @Test
    @DisplayName("map method should map all common fields from source to target and populates owner")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTargetAndPopulatesOwner() {
        when(repository.findOwner(any(Long.class)))
                .thenReturn(Optional.of("Test owner"));

        mapper.map(source, target);
        assertEquals(source.getId(), target.getId());
        assertEquals(source.getName(), target.getName());
        assertEquals(source.getDescription(), target.getDescription());
        assertEquals(source.getDifficulty(), target.getDifficulty());
        equalCategories(source, target);
        equalIngredients(source, target);
        assertEquals(source.getServings(), target.getServings());
        assertEquals(source.getCookingTime(), target.getCookingTime());
        assertEquals(source.getInstructions(), target.getInstructions());
        verify(repository, times(1))
                .findOwner(source.getId());
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new SearchRecipeResponseBody and populate owner and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewSearchRecipeResponseBodyAndPopulatesOwnerAndReturnsIt() {
        when(repository.findOwner(any(Long.class)))
                .thenReturn(Optional.of("Test owner"));
        final SearchRecipeResponseBody searchRecipeResponseBody = mapper.mapToDTO(source);
        assertEquals(source.getId(), searchRecipeResponseBody.getId());
        assertEquals(source.getName(), searchRecipeResponseBody.getName());
        assertEquals(source.getDescription(), searchRecipeResponseBody.getDescription());
        assertEquals(source.getDifficulty(), searchRecipeResponseBody.getDifficulty());
        equalCategories(source, searchRecipeResponseBody);
        equalIngredients(source, searchRecipeResponseBody);
        assertEquals(source.getServings(), searchRecipeResponseBody.getServings());
        assertEquals(source.getCookingTime(), searchRecipeResponseBody.getCookingTime());
        assertEquals(source.getInstructions(), searchRecipeResponseBody.getInstructions());
        verify(repository, times(1))
                .findOwner(source.getId());
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }

    private static void equalCategories(Recipe source, SearchRecipeResponseBody target) {
        assertIterableEquals(
                source.getCategories()
                        .stream()
                        .map(Category::getName)
                        .sorted()
                        .toList(),
                target.getCategories()
                        .stream()
                        .sorted()
                        .toList()
        );
    }

    private static void equalIngredients(Recipe source, SearchRecipeResponseBody target) {
        assertIterableEquals(
                source.getIngredients()
                        .stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList(),
                target.getIngredients()
                        .stream()
                        .map(IngredientResponseBody::getName)
                        .sorted()
                        .toList()
        );

        assertIterableEquals(
                source.getIngredients()
                        .stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList(),
                target.getIngredients()
                        .stream()
                        .map(IngredientResponseBody::getAmount)
                        .sorted()
                        .toList()
        );

        assertIterableEquals(
                source.getIngredients()
                        .stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList(),
                target.getIngredients()
                        .stream()
                        .map(IngredientResponseBody::getUnit)
                        .sorted()
                        .toList()
        );
    }
}

