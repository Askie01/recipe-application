package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = RecipeDTOToRecipeMapperConfiguration.class)
@Import(value = {
        LongIdMapperConfiguration.class,
        ImageMapperConfiguration.class,
        ImageValidatorConfiguration.class,
        StringNameMapperConfiguration.class,
        StringNameValidatorConfiguration.class,
        DescriptionMapperConfiguration.class,
        DescriptionValidatorConfiguration.class,
        DifficultyDTOToDifficultyMapperConfiguration.class,
        CategoryDTOToCategoryMapperConfiguration.class,
        LongVersionMapperConfiguration.class,
        IngredientDTOToIngredientMapperConfiguration.class,
        AmountMapperConfiguration.class,
        AmountValidatorConfiguration.class,
        MeasureUnitDTOToMeasureUnitMapperConfiguration.class,
        ServingsMapperConfiguration.class,
        ServingsValidatorConfiguration.class,
        CookingTimeMapperConfiguration.class,
        CookingTimeValidatorConfiguration.class,
        InstructionsMapperConfiguration.class,
        InstructionsValidatorConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.recipeDTO-to-recipe-type=simple",
        "component.mapper.id-type=simple-long-id",
        "component.mapper.image-type=validated-image",
        "component.validator.image-type=five-mega-bytes-image",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.description-type=validated-description",
        "component.validator.description-type=non-blank-description",
        "component.mapper.difficultyDTO-to-difficulty-type=simple",
        "component.mapper.categoryDTO-to-category-type=simple",
        "component.mapper.version-type=simple-long-version",
        "component.mapper.ingredientDTO-to-ingredient-type=simple",
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount",
        "component.mapper.measureUnitDTO-to-measureUnit-type=simple",
        "component.mapper.servings-type=validated-servings",
        "component.validator.servings-type=positive-servings",
        "component.mapper.cooking-time-type=validated-cooking-time",
        "component.validator.cooking-time-type=positive-cooking-time",
        "component.mapper.instructions-type=validated-instructions",
        "component.validator.instructions-type=non-blank-instructions"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleRecipeDTOToRecipeMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleRecipeDTOToRecipeMapperIntegrationTest {

    private RecipeDTO source;
    private Recipe target;
    private final RecipeDTOToRecipeMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeDTO();
        this.target = getTestRecipe();
    }

    private static RecipeDTO getTestRecipeDTO() {
        final CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L)
                .name("Test categoryDTO")
                .version(1L)
                .build();
        final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                .id(1L)
                .name("Test measure unitDTO")
                .version(1L)
                .build();
        final IngredientDTO ingredientDTO = IngredientDTO.builder()
                .id(1L)
                .name("Test ingredientDTO")
                .amount(1.0)
                .measureUnitDTO(measureUnitDTO)
                .version(1L)
                .build();
        return RecipeDTO.builder()
                .id(1L)
                .name("Test recipeDTO")
                .description("Test description")
                .difficultyDTO(DifficultyDTO.EASY)
                .categoryDTOs(new HashSet<>(Set.of(categoryDTO)))
                .ingredientDTOs(new HashSet<>(Set.of(ingredientDTO)))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions in RecipeDTO")
                .version(1L)
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
                .description("Test description")
                .difficulty(Difficulty.MEDIUM)
                .categories(new HashSet<>(Set.of(category)))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(2.0)
                .cookingTime(20)
                .instructions("Test instructions in Recipe")
                .version(2L)
                .build();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should map source image to target image when source is present")
    void map_whenSourceIsPresent_mapsSourceImageToTargetImage() {
        mapper.map(source, target);
        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertArrayEquals(sourceImage, targetImage);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source description to target description when source is present")
    void map_whenSourceIsPresent_mapsSourceDescriptionToTargetDescription() {
        mapper.map(source, target);
        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        assertEquals(sourceDescription, targetDescription);
    }

    @Test
    @DisplayName("map method should map source difficultyDTO to target difficulty when source is present")
    void map_whenSourceIsPresent_mapsSourceDifficultyDTOToTargetDifficulty() {
        mapper.map(source, target);
        final String difficultyDTOName = source.getDifficultyDTO().name();
        final String difficultyName = target.getDifficulty().name();
        assertEquals(difficultyDTOName, difficultyName);
    }

    @Test
    @DisplayName("map method should map source categoryDTOs to target categories when source is present")
    void map_whenSourceIsPresent_mapsSourceCategoryDTOsToTargetCategories() {
        mapper.map(source, target);
        equalCategories(source, target);
    }

    @Test
    @DisplayName("map method should map source ingredientDTOs to target ingredients when source is present")
    void map_whenSourceIsPresent_mapsSourceIngredientDTOsToTargetIngredients() {
        mapper.map(source, target);
        equalIngredients(source, target);
    }

    @Test
    @DisplayName("map method should map source servings to target servings when source is present")
    void map_whenSourceIsPresent_mapsSourceServingsToTargetServings() {
        mapper.map(source, target);
        final double sourceServings = source.getServings();
        final double targetServings = target.getServings();
        assertEquals(sourceServings, targetServings);
    }

    @Test
    @DisplayName("map method should map source cookingTime to target cookingTime when source is present")
    void map_whenSourceIsPresent_mapsSourceCookingTimeToTargetCookingTime() {
        mapper.map(source, target);
        final int sourceCookingTime = source.getCookingTime();
        final int targetCookingTime = target.getCookingTime();
        assertEquals(sourceCookingTime, targetCookingTime);
    }

    @Test
    @DisplayName("map method should map source instructions to target instructions when source is present")
    void map_whenSourceIsPresent_mapsSourceInstructionsToTargetInstructions() {
        mapper.map(source, target);
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);

        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertArrayEquals(sourceImage, targetImage);

        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);

        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        assertEquals(sourceDescription, targetDescription);

        final String difficultyDTOName = source.getDifficultyDTO().name();
        final String difficultyName = target.getDifficulty().name();
        assertEquals(difficultyDTOName, difficultyName);
        equalCategories(source, target);
        equalIngredients(source, target);

        final double sourceServings = source.getServings();
        final double targetServings = target.getServings();
        assertEquals(sourceServings, targetServings);

        final int sourceCookingTime = source.getCookingTime();
        final int targetCookingTime = target.getCookingTime();
        assertEquals(sourceCookingTime, targetCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);

        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
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
        final Long sourceId = source.getId();
        final Long recipeId = recipe.getId();
        assertEquals(sourceId, recipeId);
    }

    @Test
    @DisplayName("mapToEntity method should map source image to new recipe image when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceImageToNewRecipeImageAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final byte[] sourceImage = source.getImage();
        final byte[] recipeImage = recipe.getImage();
        assertArrayEquals(sourceImage, recipeImage);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new recipe name when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewRecipeNameAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String recipeName = recipe.getName();
        assertEquals(sourceName, recipeName);
    }

    @Test
    @DisplayName("mapToEntity method should map source description to new recipe description when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDescriptionAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final String sourceDescription = source.getDescription();
        final String recipeDescription = recipe.getDescription();
        assertEquals(sourceDescription, recipeDescription);
    }

    @Test
    @DisplayName("mapToEntity method should map source difficultyDTO to new recipe difficulty when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDifficultyDTOToNewRecipeDifficultyAndReturnThatNewRecipeObject() {
        final String difficultyName = mapper.mapToEntity(source).getDifficulty().name();
        final String difficultyDTOName = source.getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
    }

    @Test
    @DisplayName("mapToEntity method should map source categoryDTOs to new recipe categories when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCategoryDTOsToNewRecipeCategoriesAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        equalCategories(source, recipe);
    }

    @Test
    @DisplayName("mapToEntity method should map source ingredientDTOs to new recipe ingredients when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceIngredientDTOsToNewRecipeIngredientsAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        equalIngredients(source, recipe);
    }

    @Test
    @DisplayName("mapToEntity method should map source servings to new recipe servings when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceServingsToNewRecipeServingsAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final double sourceServings = source.getServings();
        final double recipeServings = recipe.getServings();
        assertEquals(sourceServings, recipeServings);
    }

    @Test
    @DisplayName("mapToEntity method should map source cookingTime to new recipe cookingTime when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeCookingTimeAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final int sourceCookingTime = source.getCookingTime();
        final int recipeCookingTime = recipe.getCookingTime();
        assertEquals(sourceCookingTime, recipeCookingTime);
    }

    @Test
    @DisplayName("mapToEntity method should map source instructions to new recipe instructions when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeInstructionsAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final String sourceInstructions = source.getInstructions();
        final String recipeInstructions = recipe.getInstructions();
        assertEquals(sourceInstructions, recipeInstructions);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new recipe version when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewRecipeVersionAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long recipeVersion = recipe.getVersion();
        assertEquals(sourceVersion, recipeVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new Recipe and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeAndReturnThatNewRecipeObject() {
        final Recipe recipe = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long recipeId = recipe.getId();
        assertEquals(sourceId, recipeId);

        final byte[] sourceImage = source.getImage();
        final byte[] recipeImage = recipe.getImage();
        assertArrayEquals(sourceImage, recipeImage);

        final String sourceName = source.getName();
        final String recipeName = recipe.getName();
        assertEquals(sourceName, recipeName);

        final String sourceDescription = source.getDescription();
        final String recipeDescription = recipe.getDescription();
        assertEquals(sourceDescription, recipeDescription);

        final String difficultyDTOName = source.getDifficultyDTO().name();
        final String difficultyName = recipe.getDifficulty().name();
        assertEquals(difficultyDTOName, difficultyName);
        equalCategories(source, recipe);
        equalIngredients(source, recipe);

        final double sourceServings = source.getServings();
        final double recipeServings = recipe.getServings();
        assertEquals(sourceServings, recipeServings);

        final int sourceCookingTime = source.getCookingTime();
        final int recipeCookingTime = recipe.getCookingTime();
        assertEquals(sourceCookingTime, recipeCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String recipeInstructions = recipe.getInstructions();
        assertEquals(sourceInstructions, recipeInstructions);

        final Long sourceVersion = source.getVersion();
        final Long recipeVersion = recipe.getVersion();
        assertEquals(sourceVersion, recipeVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }

    private static void equalCategories(RecipeDTO recipeDTO, Recipe recipe) {
        assertIterableEquals(
                recipe.getCategories().stream()
                        .map(Category::getId)
                        .sorted()
                        .toList(),
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList(),
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getCategories().stream()
                        .map(Category::getVersion)
                        .sorted()
                        .toList(),
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getVersion)
                        .sorted()
                        .toList()
        );
    }

    private static void equalIngredients(RecipeDTO recipeDTO, Recipe recipe) {
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getId)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getVersion)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getVersion)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getId)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipe.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getVersion)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getVersion)
                        .sorted()
                        .toList()
        );
    }
}