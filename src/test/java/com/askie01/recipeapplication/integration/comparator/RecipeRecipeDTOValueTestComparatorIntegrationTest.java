package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.comparator.RecipeRecipeDTOTestComparator;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.factory.RecipeDTOTestFactory;
import com.askie01.recipeapplication.factory.RecipeTestFactory;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RecipeRecipeDTOValueTestComparatorTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        ImageValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        DescriptionValueTestComparatorTestConfiguration.class,
        DifficultyDifficultyDTOValueTestComparatorTestConfiguration.class,
        CategoryCategoryDTOValueTestComparatorTestConfiguration.class,
        IngredientIngredientDTOValueTestComparatorTestConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorTestConfiguration.class,
        ServingsValueTestComparatorTestConfiguration.class,
        CookingTimeValueTestComparatorTestConfiguration.class,
        InstructionsValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        FakerTestConfiguration.class,
        RandomRecipeTestFactoryTestConfiguration.class,
        RandomDifficultyTestFactoryTestConfiguration.class,
        RandomCategoryTestFactoryTestConfiguration.class,
        RandomIngredientTestFactoryTestConfiguration.class,
        RandomMeasureUnitTestFactoryTestConfiguration.class,
        RandomRecipeDTOTestFactoryTestConfiguration.class,
        RandomDifficultyDTOTestFactoryTestConfiguration.class,
        RandomCategoryDTOTestFactoryTestConfiguration.class,
        RandomIngredientDTOTestFactoryTestConfiguration.class,
        RandomMeasureUnitDTOTestFactoryTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RecipeRecipeDTOValueTestComparator integration tests")
class RecipeRecipeDTOValueTestComparatorIntegrationTest {

    private final RecipeRecipeDTOTestComparator comparator;
    private final RecipeTestFactory recipeFactory;
    private final RecipeDTOTestFactory recipeDTOFactory;
    private Recipe recipe;
    private RecipeDTO recipeDTO;

    @BeforeEach
    void setUp() {
        this.recipe = recipeFactory.createRecipe();
        this.recipeDTO = recipeDTOFactory.createRecipeDTO();
    }

    @Test
    @DisplayName("compare method should return true when recipe and recipeDTO have the same common field values.")
    void compare_whenRecipeHaveTheSameCommonFieldValuesAsRecipeDTO_returnsTrue() {
        map(recipe, recipeDTO);
        final boolean result = comparator.compare(recipe, recipeDTO);
        assertTrue(result);
    }

    private void map(Recipe recipe, RecipeDTO recipeDTO) {
        final Long recipeId = recipe.getId();
        final byte[] recipeImage = recipe.getImage();
        final String recipeName = recipe.getName();
        final String recipeDescription = recipe.getDescription();
        final Difficulty difficulty = recipe.getDifficulty();
        final List<Category> categories = recipe.getCategories();
        final List<Ingredient> ingredients = recipe.getIngredients();
        final Double recipeServings = recipe.getServings();
        final Integer recipeCookingTime = recipe.getCookingTime();
        final String recipeInstructions = recipe.getInstructions();
        final Long recipeVersion = recipe.getVersion();

        final DifficultyDTO difficultyDTO = mapToDifficultyDTO(difficulty);
        final List<CategoryDTO> categoryDTOs = mapToCategoryDTOs(categories);
        final List<IngredientDTO> ingredientDTOs = mapToIngredientDTOs(ingredients);

        recipeDTO.setId(recipeId);
        recipeDTO.setImage(recipeImage);
        recipeDTO.setName(recipeName);
        recipeDTO.setDescription(recipeDescription);
        recipeDTO.setDifficultyDTO(difficultyDTO);
        recipeDTO.setCategoryDTOs(categoryDTOs);
        recipeDTO.setIngredientDTOs(ingredientDTOs);
        recipeDTO.setServings(recipeServings);
        recipeDTO.setCookingTime(recipeCookingTime);
        recipeDTO.setInstructions(recipeInstructions);
        recipeDTO.setVersion(recipeVersion);
    }

    private DifficultyDTO mapToDifficultyDTO(Difficulty difficulty) {
        final String difficultyName = difficulty.name();
        return DifficultyDTO.valueOf(difficultyName);
    }

    private List<CategoryDTO> mapToCategoryDTOs(List<Category> categories) {
        return categories.stream()
                .map(category -> {
                    final Long categoryId = category.getId();
                    final String categoryName = category.getName();
                    final Long categoryVersion = category.getVersion();

                    final CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(categoryId);
                    categoryDTO.setName(categoryName);
                    categoryDTO.setVersion(categoryVersion);
                    return categoryDTO;
                }).toList();
    }

    private List<IngredientDTO> mapToIngredientDTOs(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> {
                    final Long ingredientId = ingredient.getId();
                    final String ingredientName = ingredient.getName();
                    final Double ingredientAmount = ingredient.getAmount();
                    final Long ingredientVersion = ingredient.getVersion();
                    final MeasureUnit measureUnit = ingredient.getMeasureUnit();

                    final MeasureUnitDTO measureUnitDTO = mapToMeasureUnitDTO(measureUnit);
                    final IngredientDTO ingredientDTO = new IngredientDTO();
                    ingredientDTO.setId(ingredientId);
                    ingredientDTO.setName(ingredientName);
                    ingredientDTO.setAmount(ingredientAmount);
                    ingredientDTO.setVersion(ingredientVersion);
                    ingredientDTO.setMeasureUnitDTO(measureUnitDTO);
                    return ingredientDTO;
                })
                .toList();
    }

    private MeasureUnitDTO mapToMeasureUnitDTO(MeasureUnit measureUnit) {
        final Long measureUnitId = measureUnit.getId();
        final String measureUnitName = measureUnit.getName();
        final Long measureUnitVersion = measureUnit.getVersion();
        return MeasureUnitDTO.builder()
                .id(measureUnitId)
                .name(measureUnitName)
                .version(measureUnitVersion)
                .build();
    }

    @Test
    @DisplayName("compare method should return false when Recipe and RecipeDTO have different common field values.")
    void compare_whenRecipeAndRecipeDTOHaveOneCommonFieldWithDifferentValue_returnsFalse() {
        final boolean result = comparator.compare(recipe, recipeDTO);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when recipe is null")
    void compare_whenRecipeIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, recipeDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when recipeDTO is null")
    void compare_whenRecipeDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(recipe, null));
    }
}