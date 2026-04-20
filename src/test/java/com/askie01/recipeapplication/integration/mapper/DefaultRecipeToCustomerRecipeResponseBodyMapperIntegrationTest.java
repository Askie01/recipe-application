package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.dto.CustomerRecipeResponseBody;
import com.askie01.recipeapplication.dto.IngredientResponseBody;
import com.askie01.recipeapplication.mapper.DefaultRecipeToCustomerRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToCustomerRecipeResponseBodyMapper;
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

@SpringJUnitConfig(classes = DefaultRecipeToCustomerRecipeResponseBodyMapper.class)
@TestPropertySource(properties = "component.mapper.recipe-to-customer-recipe-response-body=default")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeToCustomerRecipeResponseBodyMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeToCustomerRecipeResponseBodyMapperIntegrationTest {

    private Recipe source;
    private CustomerRecipeResponseBody target;
    private final RecipeToCustomerRecipeResponseBodyMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipe();
        this.target = getTestCustomerRecipeResponseBody();
    }

    private static Recipe getTestRecipe() {
        final Category category = Category.builder()
                .id(1L)
                .name("Test category")
                .version(1L)
                .build();
        final MeasureUnit measureUnit = MeasureUnit.builder()
                .id(1L)
                .name("Test measure unit")
                .version(1L)
                .build();
        final Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .name("Test ingredient")
                .amount(1.0)
                .measureUnit(measureUnit)
                .version(1L)
                .build();
        return Recipe.builder()
                .id(1L)
                .name("Test recipe")
                .image(new byte[48])
                .description("Test description")
                .difficulty(Difficulty.EASY)
                .categories(new HashSet<>(Set.of(category)))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions in Recipe")
                .version(1L)
                .build();
    }

    private static CustomerRecipeResponseBody getTestCustomerRecipeResponseBody() {
        return CustomerRecipeResponseBody.builder()
                .id(2L)
                .name("Test customer recipe")
                .description("Test customer description")
                .difficulty(Difficulty.MEDIUM)
                .categories(new HashSet<>(Set.of("Test customer category")))
                .ingredients(new HashSet<>(Set.of(
                        new IngredientResponseBody("Test customer ingredient", 1.0, "Test customer unit")
                )))
                .servings(2.0)
                .cookingTime(20)
                .instructions("Test customer instructions")
                .build();
    }

    @Test
    @DisplayName("map method should map all common fields from source to target")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        assertEquals(source.getId(), target.getId());
        assertEquals(source.getName(), target.getName());
        assertEquals(source.getDescription(), target.getDescription());
        equalCategories(source, target);
        equalIngredients(source, target);
        assertEquals(source.getServings(), target.getServings());
        assertEquals(source.getCookingTime(), target.getCookingTime());
        assertEquals(source.getInstructions(), target.getInstructions());
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
    @DisplayName("mapToDTO method should map all common fields from source to new CustomerRecipeResponseBody and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewCustomerRecipeResponseBodyAndReturnIt() {
        final CustomerRecipeResponseBody customerRecipeResponseBody = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long customerRecipeResponseBodyId = customerRecipeResponseBody.getId();
        assertEquals(sourceId, customerRecipeResponseBodyId);

        final String sourceName = source.getName();
        final String customerRecipeResponseBodyName = customerRecipeResponseBody.getName();
        assertEquals(sourceName, customerRecipeResponseBodyName);

        final String sourceDescription = source.getDescription();
        final String customerRecipeResponseBodyDescription = customerRecipeResponseBody.getDescription();
        assertEquals(sourceDescription, customerRecipeResponseBodyDescription);

        final Difficulty sourceDifficulty = source.getDifficulty();
        final Difficulty customerRecipeResponseBodyDifficulty = customerRecipeResponseBody.getDifficulty();
        assertEquals(sourceDifficulty, customerRecipeResponseBodyDifficulty);

        equalCategories(source, customerRecipeResponseBody);
        equalIngredients(source, customerRecipeResponseBody);

        final Double sourceServings = source.getServings();
        final Double customerRecipeResponseBodyServings = customerRecipeResponseBody.getServings();
        assertEquals(sourceServings, customerRecipeResponseBodyServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer customerRecipeResponseBodyCookingTime = customerRecipeResponseBody.getCookingTime();
        assertEquals(sourceCookingTime, customerRecipeResponseBodyCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String customerRecipeResponseBodyInstructions = customerRecipeResponseBody.getInstructions();
        assertEquals(sourceInstructions, customerRecipeResponseBodyInstructions);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }

    private static void equalCategories(Recipe source, CustomerRecipeResponseBody target) {
        assertIterableEquals(
                source.getCategories()
                        .stream()
                        .map(Category::getName)
                        .sorted()
                        .toList(),
                target.getCategories()
                        .stream()
                        .sorted()
                        .toList());
    }

    private static void equalIngredients(Recipe source, CustomerRecipeResponseBody target) {
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