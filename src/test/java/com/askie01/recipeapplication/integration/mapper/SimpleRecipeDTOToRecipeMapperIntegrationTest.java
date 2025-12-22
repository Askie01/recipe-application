package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.factory.RecipeDTOTestFactory;
import com.askie01.recipeapplication.factory.RecipeTestFactory;
import com.askie01.recipeapplication.mapper.RecipeDTOToRecipeMapper;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleRecipeDTOToRecipeMapperConfiguration.class,
        ValidatedLongIdMapperConfiguration.class,
        PositiveLongIdValidatorConfiguration.class,
        ValidatedImageMapperConfiguration.class,
        FiveMegaBytesImageValidatorConfiguration.class,
        ValidatedStringNameMapperConfiguration.class,
        NonBlankStringNameValidatorConfiguration.class,
        ValidatedDescriptionMapperConfiguration.class,
        NonBlankDescriptionValidatorConfiguration.class,
        SimpleDifficultyDTOToDifficultyMapperConfiguration.class,
        SimpleCategoryDTOToCategoryMapperConfiguration.class,
        ValidatedLongVersionMapperConfiguration.class,
        PositiveLongVersionValidatorConfiguration.class,
        SimpleIngredientDTOToIngredientMapperConfiguration.class,
        ValidatedAmountMapperConfiguration.class,
        PositiveAmountValidatorConfiguration.class,
        SimpleMeasureUnitDTOToMeasureUnitMapperConfiguration.class,
        ValidatedServingsMapperConfiguration.class,
        PositiveServingsValidatorConfiguration.class,
        ValidatedCookingTimeMapperConfiguration.class,
        PositiveCookingTimeValidatorConfiguration.class,
        ValidatedInstructionsMapperConfiguration.class,
        NonBlankInstructionsValidatorConfiguration.class,

        RandomRecipeDTOTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class,
        RandomDifficultyDTOTestFactoryDefaultTestConfiguration.class,
        RandomCategoryDTOTestFactoryDefaultTestConfiguration.class,
        RandomIngredientDTOTestFactoryTestConfiguration.class,
        RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration.class,
        RandomRecipeTestFactoryTestConfiguration.class,
        RandomDifficultyTestFactoryDefaultTestConfiguration.class,
        RandomCategoryTestFactoryDefaultTestConfiguration.class,
        RandomIngredientTestFactoryDefaultTestConfiguration.class,
        RandomMeasureUnitTestFactoryDefaultTestConfiguration.class,

        RecipeRecipeDTOValueTestComparatorTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        ImageValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        DescriptionValueTestComparatorTestConfiguration.class,
        DifficultyDifficultyDTOValueTestComparatorTestConfiguration.class,
        CategoryCategoryDTOValueTestComparatorDefaultTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        IngredientIngredientDTOValueTestComparatorDefaultTestConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorDefaultTestConfiguration.class,
        ServingsValueTestComparatorTestConfiguration.class,
        CookingTimeValueTestComparatorTestConfiguration.class,
        InstructionsValueTestComparatorTestConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.recipeDTO-to-recipe-type=simple",
        "component.mapper.id-type=validated-long-id",
        "component.validator.id-type=positive-long-id",
        "component.mapper.image-type=validated-image",
        "component.validator.image-type=five-mega-bytes-image",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.description-type=validated-description",
        "component.validator.description-type=non-blank-description",
        "component.mapper.difficultyDTO-to-difficulty-type=simple",
        "component.mapper.categoryDTO-to-category-type=simple",
        "component.mapper.version-type=validated-long-version",
        "component.validator.version-type=positive-long-version",
        "component.mapper.ingredientDTO-to-ingredient-type=simple",
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount",
        "component.mapper.measureUnitDTO-to-measureUnit-type=simple",
        "component.mapper.servings-type=validated-servings",
        "component.validator.servings-type=positive-servings",
        "component.mapper.cooking-time-type=validated-cooking-time",
        "component.validator.cooking-time-type=positive-cooking-time",
        "component.mapper.instructions-type=validated-instructions",
        "component.validator.instructions-type=non-blank-instructions",
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleRecipeDTOToRecipeMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleRecipeDTOToRecipeMapperIntegrationTest {

    private final RecipeDTOToRecipeMapper mapper;

    private final RecipeRecipeDTOTestComparator recipeComparator;
    private final LongIdTestComparator idComparator;
    private final ImageTestComparator imageComparator;
    private final StringNameTestComparator nameComparator;
    private final DescriptionTestComparator descriptionComparator;
    private final DifficultyDifficultyDTOTestComparator difficultyComparator;
    private final ServingsTestComparator servingsComparator;
    private final CookingTimeTestComparator cookingTimeComparator;
    private final InstructionsTestComparator instructionsComparator;
    private final LongVersionTestComparator versionComparator;

    private final RecipeDTOTestFactory recipeDTOFactory;
    private final RecipeTestFactory recipeFactory;

    private RecipeDTO source;
    private Recipe target;

    @BeforeEach
    void setUp() {
        this.source = recipeDTOFactory.createRecipeDTO();
        this.target = recipeFactory.createRecipe();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should map source image to target image when source is present")
    void map_whenSourceIsPresent_mapsSourceImageToTargetImage() {
        mapper.map(source, target);
        final boolean equalImage = imageComparator.compare(source, target);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source description to target description when source is present")
    void map_whenSourceIsPresent_mapsSourceDescriptionToTargetDescription() {
        mapper.map(source, target);
        final boolean equalDescription = descriptionComparator.compare(source, target);
        assertTrue(equalDescription);
    }

    @Test
    @DisplayName("map method should map source difficultyDTO to target difficulty when source is present")
    void map_whenSourceIsPresent_mapsSourceDifficultyDTOToTargetDifficulty() {
        mapper.map(source, target);
        final DifficultyDTO difficultyDTO = source.getDifficultyDTO();
        final Difficulty difficulty = target.getDifficulty();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
    }

    @Test
    @DisplayName("map method should map source categoryDTOs to target categories when source is present")
    void map_whenSourceIsPresent_mapsSourceCategoryDTOsToTargetCategories() {
        mapper.map(source, target);
        assertIterableEquals(
                source.getCategoryDTOs().stream()
                        .map(CategoryDTO::getId)
                        .sorted()
                        .toList(),
                target.getCategories().stream()
                        .map(Category::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategoryDTOs().stream()
                        .map(CategoryDTO::getName)
                        .sorted()
                        .toList(),
                target.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategoryDTOs().stream()
                        .map(CategoryDTO::getVersion)
                        .sorted()
                        .toList(),
                target.getCategories().stream()
                        .map(Category::getVersion)
                        .sorted()
                        .toList()
        );
    }

    @Test
    @DisplayName("map method should map source ingredientDTOs to target ingredients when source is present")
    void map_whenSourceIsPresent_mapsSourceIngredientDTOsToTargetIngredients() {
        mapper.map(source, target);
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getId)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getName)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getAmount)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getVersion)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getVersion)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getId)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getName)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getVersion)
                        .sorted()
                        .toList(),
                target.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getVersion)
                        .sorted()
                        .toList()
        );
    }

    @Test
    @DisplayName("map method should map source servings to target servings when source is present")
    void map_whenSourceIsPresent_mapsSourceServingsToTargetServings() {
        mapper.map(source, target);
        final boolean equalServings = servingsComparator.compare(source, target);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("map method should map source cookingTime to target cookingTime when source is present")
    void map_whenSourceIsPresent_mapsSourceCookingTimeToTargetCookingTime() {
        mapper.map(source, target);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, target);
        assertTrue(equalCookingTime);
    }

    @Test
    @DisplayName("map method should map source instructions to target instructions when source is present")
    void map_whenSourceIsPresent_mapsSourceInstructionsToTargetInstructions() {
        mapper.map(source, target);
        final boolean equalInstructions = instructionsComparator.compare(source, target);
        assertTrue(equalInstructions);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final boolean equalRecipes = recipeComparator.compare(target, source);
        assertTrue(equalRecipes);
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
    @DisplayName("mapToEntity method should map source id to new recipe id when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewRecipeIdAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, recipe);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source image to new recipe image when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceImageToNewRecipeImageAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalImage = imageComparator.compare(source, recipe);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new recipe name when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewRecipeNameAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, recipe);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source description to new recipe description when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDescriptionAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalDescription = descriptionComparator.compare(source, recipe);
        assertTrue(equalDescription);
    }

    @Test
    @DisplayName("mapToEntity method should map source difficultyDTO to new recipe difficulty when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDifficultyDTOToNewRecipeDifficultyAndReturnThatNewRecipeObject() {
        final Difficulty difficulty = mapper.mapToEntity(source).getDifficulty();
        final DifficultyDTO difficultyDTO = source.getDifficultyDTO();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
    }

    @Test
    @DisplayName("mapToEntity method should map source categoryDTOs to new recipe categories when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCategoryDTOsToNewRecipeCategoriesAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        assertIterableEquals(
                source.getCategoryDTOs().stream()
                        .map(CategoryDTO::getId)
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategoryDTOs().stream()
                        .map(CategoryDTO::getName)
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategoryDTOs().stream()
                        .map(CategoryDTO::getVersion)
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getVersion)
                        .sorted()
                        .toList()
        );
    }

    @Test
    @DisplayName("mapToEntity method should map source ingredientDTOs to new recipe ingredients when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceIngredientDTOsToNewRecipeIngredientsAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getId)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getName)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getAmount)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getId)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getName)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getVersion)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getVersion)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredientDTOs().stream()
                        .map(IngredientDTO::getVersion)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getVersion)
                        .sorted()
                        .toList()
        );
    }

    @Test
    @DisplayName("mapToEntity method should map source servings to new recipe servings when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceServingsToNewRecipeServingsAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalServings = servingsComparator.compare(source, recipe);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("mapToEntity method should map source cookingTime to new recipe cookingTime when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeCookingTimeAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, recipe);
        assertTrue(equalCookingTime);
    }

    @Test
    @DisplayName("mapToEntity method should map source instructions to new recipe instructions when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeInstructionsAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalInstructions = instructionsComparator.compare(source, recipe);
        assertTrue(equalInstructions);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new recipe version when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewRecipeVersionAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, recipe);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new Recipe and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final boolean equalRecipes = recipeComparator.compare(recipe, source);
        assertTrue(equalRecipes);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}