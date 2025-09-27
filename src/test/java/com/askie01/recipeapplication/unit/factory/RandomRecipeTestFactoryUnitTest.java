package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.github.javafaker.*;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("RandomRecipeTestFactory unit test")
class RandomRecipeTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;

    @Mock
    private Lorem fakerLorem;

    @Mock
    private DateAndTime fakerDateAndTime;

    @Mock
    private Date fakerBirthday;

    @Mock
    private DifficultyTestFactory difficultyTestFactory;

    @Mock
    private CategoryTestFactory categoryTestFactory;

    @Mock
    private IngredientTestFactory ingredientTestFactory;
    private RecipeTestFactory recipeTestFactory;

    @BeforeEach
    void setUp() {
        this.recipeTestFactory = new RandomRecipeTestFactory(
                faker,
                difficultyTestFactory,
                categoryTestFactory,
                ingredientTestFactory
        );
    }

    @Test
    @DisplayName("createRecipe method should return random Recipe object")
    void createRecipe_shouldReturnRandomRecipe() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), anyBoolean())).thenReturn(10L);
        when(faker.lorem()).thenReturn(fakerLorem);
        when(fakerLorem.characters(anyInt())).thenReturn("Random text");
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Random name");
        when(difficultyTestFactory.createDifficulty()).thenReturn(Difficulty.EASY);
        when(categoryTestFactory.createCategory()).thenReturn(
                Category.builder()
                        .id(1L)
                        .name("Random category name")
                        .createdAt(LocalDateTime.now())
                        .createdBy("Random createdBy")
                        .updatedAt(LocalDateTime.now())
                        .updatedBy("Random updatedBy")
                        .version(1L)
                        .build()
        );
        when(ingredientTestFactory.createIngredient()).thenReturn(
                Ingredient.builder()
                        .id(1L)
                        .name("Random ingredient name")
                        .amount(10.0)
                        .createdAt(LocalDateTime.now())
                        .createdBy("Random createdBy")
                        .updatedAt(LocalDateTime.now())
                        .updatedBy("Random updatedBy")
                        .version(1L)
                        .build()
        );
        when(fakerNumber.randomDouble(anyInt(), anyInt(), anyInt())).thenReturn(10.5);
        when(fakerNumber.numberBetween(anyInt(), anyInt())).thenReturn(10);
        when(faker.date()).thenReturn(fakerDateAndTime);
        when(fakerDateAndTime.birthday()).thenReturn(fakerBirthday);
        when(fakerBirthday.toInstant()).thenReturn(new Date().toInstant());

        final Recipe recipe = recipeTestFactory.createRecipe();
        final Long id = recipe.getId();
        final byte[] image = recipe.getImage();
        final String name = recipe.getName();
        final String description = recipe.getDescription();
        final Difficulty difficulty = recipe.getDifficulty();
        final List<Category> categories = recipe.getCategories();
        final List<Ingredient> ingredients = recipe.getIngredients();
        final Double servings = recipe.getServings();
        final Integer cookingTime = recipe.getCookingTime();
        final String instructions = recipe.getInstructions();
        final LocalDateTime createdAt = recipe.getCreatedAt();
        final String createdBy = recipe.getCreatedBy();
        final LocalDateTime updatedAt = recipe.getUpdatedAt();
        final String updatedBy = recipe.getUpdatedBy();
        final Long version = recipe.getVersion();

        assertNotNull(recipe);
        assertNotNull(id);
        assertNotNull(image);
        assertNotNull(name);
        assertNotNull(description);
        assertNotNull(difficulty);
        assertNotNull(categories);
        assertNotNull(ingredients);
        assertNotNull(servings);
        assertNotNull(cookingTime);
        assertNotNull(instructions);
        assertNotNull(createdAt);
        assertNotNull(createdBy);
        assertNotNull(updatedAt);
        assertNotNull(updatedBy);
        assertNotNull(version);
    }
}