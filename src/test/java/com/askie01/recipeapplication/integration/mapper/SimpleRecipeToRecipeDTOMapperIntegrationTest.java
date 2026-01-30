package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.mapper.RecipeToRecipeDTOMapper;
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

@SpringJUnitConfig(classes = RecipeToRecipeDTOMapperConfiguration.class)
@Import(value = {
        LongIdMapperConfiguration.class,
        ImageMapperConfiguration.class,
        ImageValidatorConfiguration.class,
        StringNameMapperConfiguration.class,
        StringNameValidatorConfiguration.class,
        DescriptionMapperConfiguration.class,
        DescriptionValidatorConfiguration.class,
        DifficultyToDifficultyDTOMapperConfiguration.class,
        CategoryToCategoryDTOMapperConfiguration.class,
        LongVersionMapperConfiguration.class,
        IngredientToIngredientDTOMapperConfiguration.class,
        AmountMapperConfiguration.class,
        AmountValidatorConfiguration.class,
        MeasureUnitToMeasureUnitDTOMapperConfiguration.class,
        ServingsMapperConfiguration.class,
        ServingsValidatorConfiguration.class,
        CookingTimeMapperConfiguration.class,
        CookingTimeValidatorConfiguration.class,
        InstructionsMapperConfiguration.class,
        InstructionsValidatorConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.recipe-to-recipeDTO-type=simple",
        "component.mapper.image-type=validated-image",
        "component.validator.image-type=five-mega-bytes-image",
        "component.mapper.id-type=simple-long-id",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.description-type=validated-description",
        "component.validator.description-type=non-blank-description",
        "component.mapper.difficulty-to-difficultyDTO-type=simple",
        "component.mapper.category-to-categoryDTO-type=simple",
        "component.mapper.version-type=simple-long-version",
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount",
        "component.mapper.measureUnit-to-measureUnitDTO-type=simple",
        "component.mapper.servings-type=validated-servings",
        "component.validator.servings-type=positive-servings",
        "component.mapper.cooking-time-type=validated-cooking-time",
        "component.validator.cooking-time-type=positive-cooking-time",
        "component.mapper.instructions-type=validated-instructions",
        "component.validator.instructions-type=non-blank-instructions"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleRecipeToRecipeDTOMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleRecipeToRecipeDTOMapperIntegrationTest {

    private Recipe source;
    private RecipeDTO target;
    private final RecipeToRecipeDTOMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipe();
        this.target = getTestRecipeDTO();
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
                .difficulty(Difficulty.EASY)
                .categories(new HashSet<>(Set.of(category)))
                .ingredients(new HashSet<>(Set.of(ingredient)))
                .servings(2.0)
                .cookingTime(20)
                .instructions("Test instructions in Recipe")
                .version(2L)
                .build();
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
                .difficultyDTO(DifficultyDTO.MEDIUM)
                .categoryDTOs(new HashSet<>(Set.of(categoryDTO)))
                .ingredientDTOs(new HashSet<>(Set.of(ingredientDTO)))
                .servings(1.0)
                .cookingTime(10)
                .instructions("Test instructions in RecipeDTO")
                .version(1L)
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
    @DisplayName("map method should map source difficulty to target difficultyDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceDifficultyToTargetDifficultyDTO() {
        mapper.map(source, target);
        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = target.getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
    }

    @Test
    @DisplayName("map method should map source categories to target categoryDTOs when source is present")
    void map_whenSourceIsPresent_mapsSourceCategoriesToTargetCategoryDTOs() {
        mapper.map(source, target);
        equalCategories(source, target);
    }

    @Test
    @DisplayName("map method should map source ingredients to target ingredientDTOs when source is present")
    void map_whenSourceIsPresent_mapsSourceIngredientsToTargetIngredientDTOs() {
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

        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = target.getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
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
    @DisplayName("mapToDTO method should map source id to new recipeDTO id when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewRecipeDTOIdAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long recipeDTOId = recipeDTO.getId();
        assertEquals(sourceId, recipeDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source image to new recipeDTO image when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceImageToNewRecipeDTOImageAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final byte[] sourceImage = source.getImage();
        final byte[] recipeDTOImage = recipeDTO.getImage();
        assertArrayEquals(sourceImage, recipeDTOImage);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new recipeDTO name when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewRecipeDTONameAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final String sourceName = source.getName();
        final String recipeDTOName = recipeDTO.getName();
        assertEquals(sourceName, recipeDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source description to new recipeDTO description when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDTODescriptionAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final String sourceDescription = source.getDescription();
        final String recipeDTODescription = recipeDTO.getDescription();
        assertEquals(sourceDescription, recipeDTODescription);
    }

    @Test
    @DisplayName("mapToDTO method should map source difficulty to new recipeDTO difficultyDTO when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceDifficultyToNewRecipeDTODifficultyDTOAndReturnThatNewRecipeDTOObject() {
        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = mapper.mapToDTO(source).getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source categories to new recipeDTO categoryDTOs when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceCategoriesToNewRecipeDTOCategoryDTOsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        equalCategories(source, recipeDTO);
    }

    @Test
    @DisplayName("mapToDTO method should map source ingredients to new recipeDTO ingredientDTOs when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceIngredientsToNewRecipeDTOIngredientDTOsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        equalIngredients(source, recipeDTO);
    }

    @Test
    @DisplayName("mapToDTO method should map source servings to new recipeDTO servings when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceServingsToNewRecipeDTOServingsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final double sourceServings = source.getServings();
        final double recipeDTOServings = recipeDTO.getServings();
        assertEquals(sourceServings, recipeDTOServings);
    }

    @Test
    @DisplayName("mapToDTO method should map source cookingTime to new recipeDTO cookingTime when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeDTOCookingTimeAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final int sourceCookingTime = source.getCookingTime();
        final int recipeDTOCookingTime = recipeDTO.getCookingTime();
        assertEquals(sourceCookingTime, recipeDTOCookingTime);
    }

    @Test
    @DisplayName("mapToDTO method should map source instructions to new recipeDTO instructions when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeDTOInstructionsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final String sourceInstructions = source.getInstructions();
        final String recipeDTOInstructions = recipeDTO.getInstructions();
        assertEquals(sourceInstructions, recipeDTOInstructions);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new recipeDTO version when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewRecipeDTOVersionAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final Long sourceVersion = source.getVersion();
        final Long recipeDTOVersion = recipeDTO.getVersion();
        assertEquals(sourceVersion, recipeDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new RecipeDTO and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeDTOAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long recipeDTOId = recipeDTO.getId();
        assertEquals(sourceId, recipeDTOId);

        final byte[] sourceImage = source.getImage();
        final byte[] recipeDTOImage = recipeDTO.getImage();
        assertArrayEquals(sourceImage, recipeDTOImage);

        final String sourceName = source.getName();
        final String recipeDTOName = recipeDTO.getName();
        assertEquals(sourceName, recipeDTOName);

        final String sourceDescription = source.getDescription();
        final String recipeDTODescription = recipeDTO.getDescription();
        assertEquals(sourceDescription, recipeDTODescription);

        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = recipeDTO.getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
        equalCategories(source, recipeDTO);
        equalIngredients(source, recipeDTO);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }

    private static void equalCategories(Recipe recipe, RecipeDTO recipeDTO) {
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

    private static void equalIngredients(Recipe recipe, RecipeDTO recipeDTO) {
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