package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.RecipeRequestBodyToRecipeMapperConfiguration;
import com.askie01.recipeapplication.dto.IngredientRequestBody;
import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.mapper.RecipeRequestBodyToRecipeMapper;
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

@SpringJUnitConfig(classes = RecipeRequestBodyToRecipeMapperConfiguration.class)
@TestPropertySource(properties = "component.mapper.recipe-request-body-to-recipe-type=default")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeRequestBodyToRecipeMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeRequestBodyToRecipeMapperIntegrationTest {

    private RecipeRequestBody source;
    private Recipe target;
    private final RecipeRequestBodyToRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeRequestBody();
        this.target = getTestRecipe();
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
    @DisplayName("map method should map all common fields from source to target")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);

        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertArrayEquals(sourceImage, targetImage);

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

        final byte[] sourceImage = source.getImage();
        final byte[] recipeImage = recipe.getImage();
        assertArrayEquals(sourceImage, recipeImage);

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

    private static void equalCategories(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        assertIterableEquals(
                recipeRequestBody.getCategories().stream()
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList()
        );
    }

    private static void equalIngredients(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        assertIterableEquals(
                recipeRequestBody.getIngredients().stream()
                        .map(IngredientRequestBody::getName)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeRequestBody.getIngredients().stream()
                        .map(IngredientRequestBody::getAmount)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeRequestBody.getIngredients().stream()
                        .map(IngredientRequestBody::getUnit)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList()
        );
    }
}