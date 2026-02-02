package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.IngredientResponseBody;
import com.askie01.recipeapplication.dto.RecipeResponseBody;
import com.askie01.recipeapplication.mapper.DefaultRecipeToRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToRecipeResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultRecipeToRecipeResponseBodyMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultRecipeToRecipeResponseBodyMapperUnitTest {

    private Recipe source;
    private RecipeResponseBody target;
    private RecipeToRecipeResponseBodyMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipe();
        this.target = getTestRecipeResponseBody();
        this.mapper = new DefaultRecipeToRecipeResponseBodyMapper();
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

    private static RecipeResponseBody getTestRecipeResponseBody() {
        final IngredientResponseBody ingredient = IngredientResponseBody.builder()
                .name("Test name in ingredient response body")
                .amount(10.2)
                .unit("Test unit int ingredient response body")
                .build();
        return RecipeResponseBody.builder()
                .id(1L)
                .name("Test recipe response body")
                .image(new byte[24])
                .description("Test description in recipe response body")
                .difficulty(Difficulty.MEDIUM)
                .categories(new HashSet<>(Set.of("Test category in recipe response body")))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(10.2)
                .cookingTime(10)
                .instructions("Test instructions in recipe response body")
                .build();
    }

    @Test
    @DisplayName("map method should map all common fields from source to target")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);

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
    @DisplayName("mapToDTO method should map all common fields from source to new RecipeResponseBody and return that object")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeResponseBodyAndReturnIt() {
        final RecipeResponseBody recipeResponseBody = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long recipeResponseBodyId = recipeResponseBody.getId();
        assertEquals(sourceId, recipeResponseBodyId);

        final String sourceName = source.getName();
        final String recipeResponseBodyName = recipeResponseBody.getName();
        assertEquals(sourceName, recipeResponseBodyName);

        final byte[] sourceImage = source.getImage();
        final byte[] recipeResponseBodyImage = recipeResponseBody.getImage();
        assertArrayEquals(sourceImage, recipeResponseBodyImage);

        final String sourceDescription = source.getDescription();
        final String recipeResponseBodyDescription = recipeResponseBody.getDescription();
        assertEquals(sourceDescription, recipeResponseBodyDescription);

        final Difficulty sourceDifficulty = source.getDifficulty();
        final Difficulty recipeResponseBodyDifficulty = recipeResponseBody.getDifficulty();
        assertEquals(sourceDifficulty, recipeResponseBodyDifficulty);
        equalCategories(source, recipeResponseBody);
        equalIngredients(source, recipeResponseBody);

        final Double sourceServings = source.getServings();
        final Double recipeResponseBodyServings = recipeResponseBody.getServings();
        assertEquals(sourceServings, recipeResponseBodyServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer recipeResponseBodyCookingTime = recipeResponseBody.getCookingTime();
        assertEquals(sourceCookingTime, recipeResponseBodyCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String recipeResponseBodyInstructions = recipeResponseBody.getInstructions();
        assertEquals(sourceInstructions, recipeResponseBodyInstructions);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }

    private static void equalCategories(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        assertIterableEquals(
                recipe.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList(),
                recipeResponseBody.getCategories().stream()
                        .sorted()
                        .toList()
        );
    }

    private static void equalIngredients(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList(),
                recipeResponseBody.getIngredients().stream()
                        .map(IngredientResponseBody::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList(),
                recipeResponseBody.getIngredients().stream()
                        .map(IngredientResponseBody::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList(),
                recipeResponseBody.getIngredients().stream()
                        .map(IngredientResponseBody::getUnit)
                        .sorted()
                        .toList()
        );
    }
}