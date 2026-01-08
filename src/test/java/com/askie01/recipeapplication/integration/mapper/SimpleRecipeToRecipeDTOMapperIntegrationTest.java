package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.factory.RecipeDTOTestFactory;
import com.askie01.recipeapplication.factory.RecipeTestFactory;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleRecipeToRecipeDTOMapperDefaultTestConfiguration.class,
        RandomRecipeDTOTestFactoryDefaultTestConfiguration.class,
        RandomRecipeTestFactoryDefaultTestConfiguration.class,
        RecipeRecipeDTOValueTestComparatorDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        ImageValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        DescriptionValueTestComparatorTestConfiguration.class,
        DifficultyDifficultyDTOValueTestComparatorTestConfiguration.class,
        ServingsValueTestComparatorTestConfiguration.class,
        CookingTimeValueTestComparatorTestConfiguration.class,
        InstructionsValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class
})
@TestPropertySource(locations = "classpath:simple-recipe-to-recipeDTO-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleRecipeToRecipeDTOMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleRecipeToRecipeDTOMapperIntegrationTest {

    private Recipe source;
    private RecipeDTO target;
    private final RecipeTestFactory recipeFactory;
    private final RecipeDTOTestFactory recipeDTOFactory;
    private final RecipeToRecipeDTOMapper mapper;
    private final LongIdTestComparator idComparator;
    private final ImageTestComparator imageComparator;
    private final StringNameTestComparator nameComparator;
    private final DescriptionTestComparator descriptionComparator;
    private final DifficultyDifficultyDTOTestComparator difficultyComparator;
    private final ServingsTestComparator servingsComparator;
    private final CookingTimeTestComparator cookingTimeComparator;
    private final InstructionsTestComparator instructionsComparator;
    private final LongVersionTestComparator versionComparator;
    private final RecipeRecipeDTOTestComparator recipeComparator;

    @BeforeEach
    void setUp() {
        this.source = recipeFactory.createRecipe();
        this.target = recipeDTOFactory.createRecipeDTO();
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
    @DisplayName("map method should map source difficulty to target difficultyDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceDifficultyToTargetDifficultyDTO() {
        mapper.map(source, target);
        final Difficulty difficulty = source.getDifficulty();
        final DifficultyDTO difficultyDTO = target.getDifficultyDTO();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
    }

    @Test
    @DisplayName("map method should map source categories to target categoryDTOs when source is present")
    void map_whenSourceIsPresent_mapsSourceCategoriesToTargetCategoryDTOs() {
        mapper.map(source, target);
        assertIterableEquals(
                source.getCategories().stream()
                        .map(Category::getId)
                        .sorted()
                        .toList(),
                target.getCategoryDTOs().stream()
                        .map(CategoryDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList(),
                target.getCategoryDTOs().stream()
                        .map(CategoryDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategories().stream()
                        .map(Category::getVersion)
                        .sorted()
                        .toList(),
                target.getCategoryDTOs().stream()
                        .map(CategoryDTO::getVersion)
                        .sorted()
                        .toList()
        );
    }

    @Test
    @DisplayName("map method should map source ingredients to target ingredientDTOs when source is present")
    void map_whenSourceIsPresent_mapsSourceIngredientsToTargetIngredientDTOs() {
        mapper.map(source, target);
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getId)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getVersion)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getVersion)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getId)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getName)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getMeasureUnit)
                        .map(MeasureUnit::getVersion)
                        .sorted()
                        .toList(),
                target.getIngredientDTOs().stream()
                        .map(IngredientDTO::getMeasureUnitDTO)
                        .map(MeasureUnitDTO::getVersion)
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
        final boolean equalRecipes = recipeComparator.compare(source, target);
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
    @DisplayName("mapToDTO method should map source id to new recipeDTO id when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewRecipeDTOIdAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalId = idComparator.compare(source, recipeDTO);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToDTO method should map source image to new recipeDTO image when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceImageToNewRecipeDTOImageAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalImage = imageComparator.compare(source, recipeDTO);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new recipeDTO name when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewRecipeDTONameAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalName = nameComparator.compare(source, recipeDTO);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToDTO method should map source description to new recipeDTO description when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDTODescriptionAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalDescription = descriptionComparator.compare(source, recipeDTO);
        assertTrue(equalDescription);
    }

    @Test
    @DisplayName("mapToDTO method should map source difficulty to new recipeDTO difficultyDTO when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceDifficultyToNewRecipeDTODifficultyDTOAndReturnThatNewRecipeDTOObject() {
        final Difficulty difficulty = source.getDifficulty();
        final DifficultyDTO difficultyDTO = mapper.mapToDTO(source).getDifficultyDTO();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
    }

    @Test
    @DisplayName("mapToDTO method should map source categories to new recipeDTO categoryDTOs when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceCategoriesToNewRecipeDTOCategoryDTOsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        assertIterableEquals(
                source.getCategories().stream()
                        .map(Category::getId)
                        .sorted()
                        .toList(),
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList(),
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getCategories().stream()
                        .map(Category::getVersion)
                        .sorted()
                        .toList(),
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getVersion)
                        .sorted()
                        .toList()
        );
    }

    @Test
    @DisplayName("mapToDTO method should map source ingredients to new recipeDTO ingredientDTOs when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceIngredientsToNewRecipeDTOIngredientDTOsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getId)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
                        .map(Ingredient::getVersion)
                        .sorted()
                        .toList(),
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getVersion)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                source.getIngredients().stream()
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
                source.getIngredients().stream()
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
                source.getIngredients().stream()
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

    @Test
    @DisplayName("mapToDTO method should map source servings to new recipeDTO servings when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceServingsToNewRecipeDTOServingsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalServings = servingsComparator.compare(source, recipeDTO);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("mapToDTO method should map source cookingTime to new recipeDTO cookingTime when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeDTOCookingTimeAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, recipeDTO);
        assertTrue(equalCookingTime);
    }

    @Test
    @DisplayName("mapToDTO method should map source instructions to new recipeDTO instructions when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeDTOInstructionsAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalInstructions = instructionsComparator.compare(source, recipeDTO);
        assertTrue(equalInstructions);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new recipeDTO version when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewRecipeDTOVersionAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalVersion = versionComparator.compare(source, recipeDTO);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new RecipeDTO and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeDTOAndReturnThatNewRecipeDTOObject() {
        final RecipeDTO recipeDTO = mapper.mapToDTO(source);
        final boolean equalRecipes = recipeComparator.compare(source, recipeDTO);
        assertTrue(equalRecipes);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}