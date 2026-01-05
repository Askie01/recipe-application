package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RecipeRecipeDTOValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RecipeRecipeDTOValueTestComparatorUnitTest {

    private Recipe recipe;
    private RecipeDTO recipeDTO;

    @Mock
    private LongIdTestComparator idComparator;

    @Mock
    private ImageTestComparator imageComparator;

    @Mock
    private StringNameTestComparator nameComparator;

    @Mock
    private DescriptionTestComparator descriptionComparator;

    @Mock
    private DifficultyDifficultyDTOTestComparator difficultyComparator;

    @Mock
    private CategoryCategoryDTOTestComparator categoryComparator;

    @Mock
    private IngredientIngredientDTOTestComparator ingredientComparator;

    @Mock
    private ServingsTestComparator servingsComparator;

    @Mock
    private CookingTimeTestComparator cookingTimeComparator;

    @Mock
    private InstructionsTestComparator instructionsComparator;

    @Mock
    private LongVersionTestComparator versionComparator;
    private RecipeRecipeDTOTestComparator recipeComparator;

    @BeforeEach
    void setUp() {
        this.recipeComparator = new RecipeRecipeDTOValueTestComparator(
                idComparator,
                imageComparator,
                nameComparator,
                descriptionComparator,
                difficultyComparator,
                categoryComparator,
                ingredientComparator,
                servingsComparator,
                cookingTimeComparator,
                instructionsComparator,
                versionComparator
        );
        final Faker faker = new Faker();
        final RecipeTestFactory recipeFactory = getRecipeTestFactory(faker);
        final RecipeDTOTestFactory recipeDTOFactory = getRecipeDTOTestFactory(faker);
        this.recipe = recipeFactory.createRecipe();
        this.recipeDTO = recipeDTOFactory.createRecipeDTO();
    }

    private static @NotNull RecipeDTOTestFactory getRecipeDTOTestFactory(Faker faker) {
        final DifficultyDTOTestFactory difficultyDTOFactory = new RandomDifficultyDTOTestFactory(faker);
        final CategoryDTOTestFactory categoryDTOFactory = new RandomCategoryDTOTestFactory(faker);
        final MeasureUnitDTOTestFactory measureUnitDTOFactory = new RandomMeasureUnitDTOTestFactory(faker);
        final IngredientDTOTestFactory ingredientDTOFactory = new RandomIngredientDTOTestFactory(faker, measureUnitDTOFactory);
        return new RandomRecipeDTOTestFactory(
                faker,
                difficultyDTOFactory,
                categoryDTOFactory,
                ingredientDTOFactory
        );
    }

    private static @NotNull RecipeTestFactory getRecipeTestFactory(Faker faker) {
        final DifficultyTestFactory difficultyFactory = new RandomDifficultyTestFactory(faker);
        final CategoryTestFactory categoryFactory = new RandomCategoryTestFactory(faker);
        final MeasureUnitTestFactory measureUnitFactory = new RandomMeasureUnitTestFactory(faker);
        final IngredientTestFactory ingredientFactory = new RandomIngredientTestFactory(faker, measureUnitFactory);
        return new RandomRecipeTestFactory(
                faker,
                difficultyFactory,
                categoryFactory,
                ingredientFactory
        );
    }

    @Test
    @DisplayName("compare method should return true when recipe and recipeDTO have the same common field values.")
    void compare_whenRecipeHaveTheSameCommonFieldValuesAsRecipeDTO_returnsTrue() {
        when(idComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(imageComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(nameComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(descriptionComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(difficultyComparator
                .compare(any(Difficulty.class), any(DifficultyDTO.class)))
                .thenReturn(true);
        when(categoryComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(true);
        when(ingredientComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(true);
        when(servingsComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(cookingTimeComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(instructionsComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        when(versionComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(true);
        final boolean result = recipeComparator.compare(recipe, recipeDTO);
        assertTrue(result);
    }

    @ParameterizedTest(name = "compare method should return false when Recipe and RecipeDTO have one common field with different value")
    @MethodSource("elevenBooleans")
    void compare_whenRecipeAndRecipeDTOHaveOneCommonFieldWithDifferentValue_returnsFalse(boolean equalId,
                                                                                         boolean equalImage,
                                                                                         boolean equalName,
                                                                                         boolean equalDescription,
                                                                                         boolean equalDifficulty,
                                                                                         boolean equalCategories,
                                                                                         boolean equalIngredients,
                                                                                         boolean equalServings,
                                                                                         boolean equalCookingTime,
                                                                                         boolean equalInstructions,
                                                                                         boolean equalVersion) {
        when(idComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalId);
        when(imageComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalImage);
        when(nameComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalName);
        when(descriptionComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalDescription);
        when(difficultyComparator
                .compare(any(Difficulty.class), any(DifficultyDTO.class)))
                .thenReturn(equalDifficulty);
        when(categoryComparator
                .compare(any(Category.class), any(CategoryDTO.class)))
                .thenReturn(equalCategories);
        when(ingredientComparator
                .compare(any(Ingredient.class), any(IngredientDTO.class)))
                .thenReturn(equalIngredients);
        when(servingsComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalServings);
        when(cookingTimeComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalCookingTime);
        when(instructionsComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalInstructions);
        when(versionComparator
                .compare(any(Recipe.class), any(RecipeDTO.class)))
                .thenReturn(equalVersion);
        final boolean result = recipeComparator.compare(recipe, recipeDTO);
        assertFalse(result);
    }

    private static Stream<Arguments> elevenBooleans() {
        return Stream.of(
                Arguments.of(false, true, true, true, true, true, true, true, true, true, true),
                Arguments.of(true, false, true, true, true, true, true, true, true, true, true),
                Arguments.of(true, true, false, true, true, true, true, true, true, true, true),
                Arguments.of(true, true, true, false, true, true, true, true, true, true, true),
                Arguments.of(true, true, true, true, false, true, true, true, true, true, true),
                Arguments.of(true, true, true, true, true, false, true, true, true, true, true),
                Arguments.of(true, true, true, true, true, true, false, true, true, true, true),
                Arguments.of(true, true, true, true, true, true, true, false, true, true, true),
                Arguments.of(true, true, true, true, true, true, true, true, false, true, true),
                Arguments.of(true, true, true, true, true, true, true, true, true, false, true),
                Arguments.of(true, true, true, true, true, true, true, true, true, true, false)
        );
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when recipe is null")
    void compare_whenRecipeIsNull_throwsNullPointerException() {
        when(idComparator
                .compare(isNull(), any(RecipeDTO.class)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> recipeComparator.compare(null, recipeDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when recipeDTO is null")
    void compare_whenRecipeDTOIsNull_throwsNullPointerException() {
        when(idComparator
                .compare(any(Recipe.class), isNull()))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> recipeComparator.compare(recipe, null));
    }
}