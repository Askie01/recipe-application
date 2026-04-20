package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.mapper.CustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.mapper.DefaultCustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = DefaultCustomerRecipeRequestBodyToRecipeMapper.class)
@TestPropertySource(properties = "component.mapper.customer-recipe-request-body-to-recipe-type=default")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultCustomerRecipeRequestBodyToRecipeMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultCustomerRecipeRequestBodyToRecipeMapperIntegrationTest {

    private CustomerRecipeRequestBody source;
    private Recipe target;
    private final CustomerRecipeRequestBodyToRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestCustomerRecipeRequestBody();
        this.target = getTestRecipe();
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

    @Test
    @DisplayName("map method should map all common fields from source to target")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);

        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        assertEquals(sourceDescription, targetDescription);

        final Difficulty sourceDifficulty = source.getDifficulty();
        final Difficulty targetDifficulty = target.getDifficulty();
        assertEquals(sourceDifficulty, targetDifficulty);

        equalCategories(source, target);
        equalIngredients(source, target);

        final Double sourceServings = source.getServings();
        final Double targetServings = target.getServings();
        assertEquals(sourceServings, targetServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer targetCookingTime = target.getCookingTime();
        assertEquals(sourceCookingTime, targetCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);
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
    @DisplayName("mapToEntity method should map all common fields from source to new Recipe and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeAndReturnIt() {
        final Recipe recipe = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String recipeName = recipe.getName();
        assertEquals(sourceName, recipeName);

        final String sourceDescription = source.getDescription();
        final String recipeDescription = recipe.getDescription();
        assertEquals(sourceDescription, recipeDescription);

        final Difficulty sourceDifficulty = source.getDifficulty();
        final Difficulty recipeDifficulty = recipe.getDifficulty();
        assertEquals(sourceDifficulty, recipeDifficulty);

        equalCategories(source, recipe);
        equalIngredients(source, recipe);

        final Double sourceServings = source.getServings();
        final Double recipeServings = recipe.getServings();
        assertEquals(sourceServings, recipeServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer recipeCookingTime = recipe.getCookingTime();
        assertEquals(sourceCookingTime, recipeCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String recipeInstructions = recipe.getInstructions();
        assertEquals(sourceInstructions, recipeInstructions);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }

    private static void equalCategories(CustomerRecipeRequestBody source, Recipe target) {
        assertIterableEquals(
                source.getCategories().stream()
                        .sorted()
                        .toList(),
                target.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList()
        );
    }

    private static void equalIngredients(CustomerRecipeRequestBody source, Recipe target) {
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(IngredientRequestBody::getName)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList()
        );

        assertIterableEquals(
                source.getIngredients().stream()
                        .map(IngredientRequestBody::getAmount)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList()
        );

        assertIterableEquals(
                source.getIngredients().stream()
                        .map(IngredientRequestBody::getUnit)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList()
        );
    }
}