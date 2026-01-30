package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.model.value.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimpleRecipeDTOToRecipeMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleRecipeDTOToRecipeMapperUnitTest {

    private RecipeDTO source;
    private Recipe target;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private DescriptionMapper descriptionMapper;

    @Mock
    private DifficultyDTOToDifficultyMapper difficultyMapper;

    @Mock
    private CategoryDTOToCategoryMapper categoryMapper;

    @Mock
    private IngredientDTOToIngredientMapper ingredientMapper;

    @Mock
    private ServingsMapper servingsMapper;

    @Mock
    private CookingTimeMapper cookingTimeMapper;

    @Mock
    private InstructionsMapper instructionsMapper;

    @Mock
    private LongVersionMapper versionMapper;
    private RecipeDTOToRecipeMapper recipeMapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipeDTO();
        this.target = getTestRecipe();
        this.recipeMapper = new SimpleRecipeDTOToRecipeMapper(
                idMapper,
                imageMapper,
                nameMapper,
                descriptionMapper,
                difficultyMapper,
                categoryMapper,
                ingredientMapper,
                servingsMapper,
                cookingTimeMapper,
                instructionsMapper,
                versionMapper
        );
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
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        recipeMapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should map source image to target image when source is present")
    void map_whenSourceIsPresent_mapsSourceImageToTargetImage() {
        doAnswer(invocation -> {
            final HasImage source = invocation.getArgument(0);
            final HasImage target = invocation.getArgument(1);
            final byte[] sourceImage = source.getImage();
            target.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class)
        );
        recipeMapper.map(source, target);
        final byte[] sourceImage = source.getImage();
        final byte[] targetImage = target.getImage();
        assertArrayEquals(sourceImage, targetImage);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        recipeMapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source description to target description when source is present")
    void map_whenSourceIsPresent_mapsSourceDescriptionToTargetDescription() {
        doAnswer(invocation -> {
            final HasDescription source = invocation.getArgument(0);
            final HasDescription target = invocation.getArgument(1);
            final String sourceDescription = source.getDescription();
            target.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class)
        );
        recipeMapper.map(source, target);
        final String sourceDescription = source.getDescription();
        final String targetDescription = target.getDescription();
        assertEquals(sourceDescription, targetDescription);
    }

    @Test
    @DisplayName("map method should map source difficultyDTO to target difficulty when source is present")
    void map_whenSourceIsPresent_mapsSourceDifficultyDTOToTargetDifficulty() {
        when(difficultyMapper.mapToValue(any(DifficultyDTO.class)))
                .thenAnswer(invocation -> {
                    final DifficultyDTO difficultyDTO = invocation.getArgument(0);
                    final String difficultyDTOName = difficultyDTO.name();
                    return Difficulty.valueOf(difficultyDTOName);
                });
        recipeMapper.map(source, target);
        final String difficultyDTOName = source.getDifficultyDTO().name();
        final String difficultyName = target.getDifficulty().name();
        assertEquals(difficultyDTOName, difficultyName);
    }

    @Test
    @DisplayName("map method should map source categoryDTOs to target categories when source is present")
    void map_whenSourceIsPresent_mapsSourceCategoryDTOsToTargetCategories() {
        when(categoryMapper.mapToEntity(any(CategoryDTO.class)))
                .thenAnswer(invocation -> {
                    final CategoryDTO categoryDTO = invocation.getArgument(0);
                    final Long categoryDTOId = categoryDTO.getId();
                    final String categoryDTOName = categoryDTO.getName();
                    final Long categoryDTOVersion = categoryDTO.getVersion();
                    return Category.builder()
                            .id(categoryDTOId)
                            .name(categoryDTOName)
                            .version(categoryDTOVersion)
                            .build();
                });
        recipeMapper.map(source, target);
        equalCategories(source, target);
    }

    @Test
    @DisplayName("map method should map source ingredientDTOs to target ingredients when source is present")
    void map_whenSourceIsPresent_mapsSourceIngredientDTOsToTargetIngredients() {
        when(ingredientMapper.mapToEntity(any(IngredientDTO.class)))
                .thenAnswer(invocation -> {
                    final IngredientDTO ingredientDTO = invocation.getArgument(0);
                    final Long ingredientDTOId = ingredientDTO.getId();
                    final String ingredientDTOName = ingredientDTO.getName();
                    final Double ingredientDTOAmount = ingredientDTO.getAmount();
                    final Long ingredientDTOVersion = ingredientDTO.getVersion();

                    final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
                    final Long measureUnitDTOId = measureUnitDTO.getId();
                    final String measureUnitDTOName = measureUnitDTO.getName();
                    final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
                    final MeasureUnit measureUnit = MeasureUnit.builder()
                            .id(measureUnitDTOId)
                            .name(measureUnitDTOName)
                            .version(measureUnitDTOVersion)
                            .build();

                    return Ingredient.builder()
                            .id(ingredientDTOId)
                            .name(ingredientDTOName)
                            .amount(ingredientDTOAmount)
                            .measureUnit(measureUnit)
                            .version(ingredientDTOVersion)
                            .build();
                });
        recipeMapper.map(source, target);
        equalIngredients(source, target);
    }

    @Test
    @DisplayName("map method should map source servings to target servings when source is present")
    void map_whenSourceIsPresent_mapsSourceServingsToTargetServings() {
        doAnswer(invocation -> {
            final HasServings source = invocation.getArgument(0);
            final HasServings target = invocation.getArgument(1);
            final Double sourceServings = source.getServings();
            target.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class)
        );
        recipeMapper.map(source, target);
        final Double sourceServings = source.getServings();
        final Double targetServings = target.getServings();
        assertEquals(sourceServings, targetServings);
    }

    @Test
    @DisplayName("map method should map source cookingTime to target cookingTime when source is present")
    void map_whenSourceIsPresent_mapsSourceCookingTimeToTargetCookingTime() {
        doAnswer(invocation -> {
            final HasCookingTime source = invocation.getArgument(0);
            final HasCookingTime target = invocation.getArgument(1);
            final Integer sourceCookingTime = source.getCookingTime();
            target.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class)
        );
        recipeMapper.map(source, target);
        final Integer sourceCookingTime = source.getCookingTime();
        final Integer targetCookingTime = target.getCookingTime();
        assertEquals(sourceCookingTime, targetCookingTime);
    }

    @Test
    @DisplayName("map method should map source instructions to target instructions when source is present")
    void map_whenSourceIsPresent_mapsSourceInstructionsToTargetInstructions() {
        doAnswer(invocation -> {
            final HasInstructions source = invocation.getArgument(0);
            final HasInstructions target = invocation.getArgument(1);
            final String sourceInstructions = source.getInstructions();
            target.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class)
        );
        recipeMapper.map(source, target);
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        recipeMapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        doAnswer(invocation -> {
            final HasImage source = invocation.getArgument(0);
            final HasImage target = invocation.getArgument(1);
            final byte[] sourceImage = source.getImage();
            target.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class)
        );
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        doAnswer(invocation -> {
            final HasDescription source = invocation.getArgument(0);
            final HasDescription target = invocation.getArgument(1);
            final String sourceDescription = source.getDescription();
            target.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class)
        );
        when(difficultyMapper.mapToValue(any(DifficultyDTO.class)))
                .thenAnswer(invocation -> {
                    final DifficultyDTO difficultyDTO = invocation.getArgument(0);
                    final String difficultyDTOName = difficultyDTO.name();
                    return Difficulty.valueOf(difficultyDTOName);
                });
        when(categoryMapper.mapToEntity(any(CategoryDTO.class)))
                .thenAnswer(invocation -> {
                    final CategoryDTO categoryDTO = invocation.getArgument(0);
                    final Long categoryDTOId = categoryDTO.getId();
                    final String categoryDTOName = categoryDTO.getName();
                    final Long categoryDTOVersion = categoryDTO.getVersion();
                    return Category.builder()
                            .id(categoryDTOId)
                            .name(categoryDTOName)
                            .version(categoryDTOVersion)
                            .build();
                });
        when(ingredientMapper.mapToEntity(any(IngredientDTO.class)))
                .thenAnswer(invocation -> {
                    final IngredientDTO ingredientDTO = invocation.getArgument(0);
                    final Long ingredientDTOId = ingredientDTO.getId();
                    final String ingredientDTOName = ingredientDTO.getName();
                    final Double ingredientDTOAmount = ingredientDTO.getAmount();
                    final Long ingredientDTOVersion = ingredientDTO.getVersion();

                    final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
                    final Long measureUnitDTOId = measureUnitDTO.getId();
                    final String measureUnitDTOName = measureUnitDTO.getName();
                    final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
                    final MeasureUnit measureUnit = MeasureUnit.builder()
                            .id(measureUnitDTOId)
                            .name(measureUnitDTOName)
                            .version(measureUnitDTOVersion)
                            .build();

                    return Ingredient.builder()
                            .id(ingredientDTOId)
                            .name(ingredientDTOName)
                            .amount(ingredientDTOAmount)
                            .measureUnit(measureUnit)
                            .version(ingredientDTOVersion)
                            .build();
                });
        doAnswer(invocation -> {
            final HasServings source = invocation.getArgument(0);
            final HasServings target = invocation.getArgument(1);
            final Double sourceServings = source.getServings();
            target.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class)
        );
        doAnswer(invocation -> {
            final HasCookingTime source = invocation.getArgument(0);
            final HasCookingTime target = invocation.getArgument(1);
            final Integer sourceCookingTime = source.getCookingTime();
            target.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class)
        );
        doAnswer(invocation -> {
            final HasInstructions source = invocation.getArgument(0);
            final HasInstructions target = invocation.getArgument(1);
            final String sourceInstructions = source.getInstructions();
            target.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class)
        );
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        recipeMapper.map(source, target);
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

        final Double sourceServings = source.getServings();
        final Double targetServings = target.getServings();
        assertEquals(sourceServings, targetServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer targetCookingTime = target.getCookingTime();
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
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Recipe.class));
        assertThrows(NullPointerException.class, () -> recipeMapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(RecipeDTO.class), isNull());
        assertThrows(NullPointerException.class, () -> recipeMapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new recipe id when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewRecipeIdAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long recipeId = recipe.getId();
        assertEquals(sourceId, recipeId);
    }

    @Test
    @DisplayName("mapToEntity method should map source image to new recipe image when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceImageToNewRecipeImageAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasImage source = invocation.getArgument(0);
            final HasImage target = invocation.getArgument(1);
            final byte[] sourceImage = source.getImage();
            target.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final byte[] sourceImage = source.getImage();
        final byte[] recipeImage = recipe.getImage();
        assertArrayEquals(sourceImage, recipeImage);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new recipe name when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewRecipeNameAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String recipeName = recipe.getName();
        assertEquals(sourceName, recipeName);
    }

    @Test
    @DisplayName("mapToEntity method should map source description to new recipe description when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDescriptionAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasDescription source = invocation.getArgument(0);
            final HasDescription target = invocation.getArgument(1);
            final String sourceDescription = source.getDescription();
            target.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final String sourceDescription = source.getDescription();
        final String recipeDescription = recipe.getDescription();
        assertEquals(sourceDescription, recipeDescription);
    }

    @Test
    @DisplayName("mapToEntity method should map source difficultyDTO to new recipe difficulty when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDifficultyDTOToNewRecipeDifficultyAndReturnThatNewRecipeObject() {
        when(difficultyMapper.mapToValue(any(DifficultyDTO.class)))
                .thenAnswer(invocation -> {
                    final DifficultyDTO difficultyDTO = invocation.getArgument(0);
                    final String difficultyDTOName = difficultyDTO.name();
                    return Difficulty.valueOf(difficultyDTOName);
                });
        final String difficultyDTOName = source.getDifficultyDTO().name();
        final String difficultyName = recipeMapper.mapToEntity(source).getDifficulty().name();
        assertEquals(difficultyDTOName, difficultyName);
    }

    @Test
    @DisplayName("mapToEntity method should map source categoryDTOs to new recipe categories when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCategoryDTOsToNewRecipeCategoriesAndReturnThatNewRecipeObject() {
        when(categoryMapper.mapToEntity(any(CategoryDTO.class)))
                .thenAnswer(invocation -> {
                    final CategoryDTO categoryDTO = invocation.getArgument(0);
                    final Long categoryDTOId = categoryDTO.getId();
                    final String categoryDTOName = categoryDTO.getName();
                    final Long categoryDTOVersion = categoryDTO.getVersion();
                    return Category.builder()
                            .id(categoryDTOId)
                            .name(categoryDTOName)
                            .version(categoryDTOVersion)
                            .build();
                });
        final Recipe recipe = recipeMapper.mapToEntity(source);
        equalCategories(source, recipe);
    }

    @Test
    @DisplayName("mapToEntity method should map source ingredientDTOs to new recipe ingredients when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceIngredientDTOsToNewRecipeIngredientsAndReturnThatNewRecipeObject() {
        when(ingredientMapper.mapToEntity(any(IngredientDTO.class)))
                .thenAnswer(invocation -> {
                    final IngredientDTO ingredientDTO = invocation.getArgument(0);
                    final Long ingredientDTOId = ingredientDTO.getId();
                    final String ingredientDTOName = ingredientDTO.getName();
                    final Double ingredientDTOAmount = ingredientDTO.getAmount();
                    final Long ingredientDTOVersion = ingredientDTO.getVersion();

                    final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
                    final Long measureUnitDTOId = measureUnitDTO.getId();
                    final String measureUnitDTOName = measureUnitDTO.getName();
                    final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
                    final MeasureUnit measureUnit = MeasureUnit.builder()
                            .id(measureUnitDTOId)
                            .name(measureUnitDTOName)
                            .version(measureUnitDTOVersion)
                            .build();

                    return Ingredient.builder()
                            .id(ingredientDTOId)
                            .name(ingredientDTOName)
                            .amount(ingredientDTOAmount)
                            .measureUnit(measureUnit)
                            .version(ingredientDTOVersion)
                            .build();
                });
        final Recipe recipe = recipeMapper.mapToEntity(source);
        equalIngredients(source, recipe);
    }

    @Test
    @DisplayName("mapToEntity method should map source servings to new recipe servings when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceServingsToNewRecipeServingsAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasServings source = invocation.getArgument(0);
            final HasServings target = invocation.getArgument(1);
            final Double sourceServings = source.getServings();
            target.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final Double sourceServings = source.getServings();
        final Double recipeServings = recipe.getServings();
        assertEquals(sourceServings, recipeServings);
    }

    @Test
    @DisplayName("mapToEntity method should map source cookingTime to new recipe cookingTime when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeCookingTimeAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasCookingTime source = invocation.getArgument(0);
            final HasCookingTime target = invocation.getArgument(1);
            final Integer sourceCookingTime = source.getCookingTime();
            target.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final Integer sourceCookingTime = source.getCookingTime();
        final Integer recipeCookingTime = recipe.getCookingTime();
        assertEquals(sourceCookingTime, recipeCookingTime);
    }

    @Test
    @DisplayName("mapToEntity method should map source instructions to new recipe instructions when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeInstructionsAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasInstructions source = invocation.getArgument(0);
            final HasInstructions target = invocation.getArgument(1);
            final String sourceInstructions = source.getInstructions();
            target.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final String sourceInstructions = source.getInstructions();
        final String recipeInstructions = recipe.getInstructions();
        assertEquals(sourceInstructions, recipeInstructions);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new recipe version when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewRecipeVersionAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long recipeVersion = recipe.getVersion();
        assertEquals(sourceVersion, recipeVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new Recipe and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        doAnswer(invocation -> {
            final HasImage source = invocation.getArgument(0);
            final HasImage target = invocation.getArgument(1);
            final byte[] sourceImage = source.getImage();
            target.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class)
        );
        doAnswer(invocation -> {
            final HasStringName source = invocation.getArgument(0);
            final HasStringName target = invocation.getArgument(1);
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class)
        );
        doAnswer(invocation -> {
            final HasDescription source = invocation.getArgument(0);
            final HasDescription target = invocation.getArgument(1);
            final String sourceDescription = source.getDescription();
            target.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class)
        );
        when(difficultyMapper.mapToValue(any(DifficultyDTO.class)))
                .thenAnswer(invocation -> {
                    final DifficultyDTO difficultyDTO = invocation.getArgument(0);
                    final String difficultyDTOName = difficultyDTO.name();
                    return Difficulty.valueOf(difficultyDTOName);
                });
        when(categoryMapper.mapToEntity(any(CategoryDTO.class)))
                .thenAnswer(invocation -> {
                    final CategoryDTO categoryDTO = invocation.getArgument(0);
                    final Long categoryDTOId = categoryDTO.getId();
                    final String categoryDTOName = categoryDTO.getName();
                    final Long categoryDTOVersion = categoryDTO.getVersion();
                    return Category.builder()
                            .id(categoryDTOId)
                            .name(categoryDTOName)
                            .version(categoryDTOVersion)
                            .build();
                });
        when(ingredientMapper.mapToEntity(any(IngredientDTO.class)))
                .thenAnswer(invocation -> {
                    final IngredientDTO ingredientDTO = invocation.getArgument(0);
                    final Long ingredientDTOId = ingredientDTO.getId();
                    final String ingredientDTOName = ingredientDTO.getName();
                    final Double ingredientDTOAmount = ingredientDTO.getAmount();
                    final Long ingredientDTOVersion = ingredientDTO.getVersion();

                    final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
                    final Long measureUnitDTOId = measureUnitDTO.getId();
                    final String measureUnitDTOName = measureUnitDTO.getName();
                    final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
                    final MeasureUnit measureUnit = MeasureUnit.builder()
                            .id(measureUnitDTOId)
                            .name(measureUnitDTOName)
                            .version(measureUnitDTOVersion)
                            .build();

                    return Ingredient.builder()
                            .id(ingredientDTOId)
                            .name(ingredientDTOName)
                            .amount(ingredientDTOAmount)
                            .measureUnit(measureUnit)
                            .version(ingredientDTOVersion)
                            .build();
                });
        doAnswer(invocation -> {
            final HasServings source = invocation.getArgument(0);
            final HasServings target = invocation.getArgument(1);
            final Double sourceServings = source.getServings();
            target.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class)
        );
        doAnswer(invocation -> {
            final HasCookingTime source = invocation.getArgument(0);
            final HasCookingTime target = invocation.getArgument(1);
            final Integer sourceCookingTime = source.getCookingTime();
            target.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class)
        );
        doAnswer(invocation -> {
            final HasInstructions source = invocation.getArgument(0);
            final HasInstructions target = invocation.getArgument(1);
            final String sourceInstructions = source.getInstructions();
            target.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class)
        );
        doAnswer(invocation -> {
            final HasLongVersion source = invocation.getArgument(0);
            final HasLongVersion target = invocation.getArgument(1);
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class)
        );
        final Recipe recipe = recipeMapper.mapToEntity(source);
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

        final Double sourceServings = source.getServings();
        final Double recipeServings = recipe.getServings();
        assertEquals(sourceServings, recipeServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer recipeCookingTime = recipe.getCookingTime();
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
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(HasLongId.class));
        assertThrows(NullPointerException.class, () -> recipeMapper.mapToEntity(null));
    }

    private static void equalCategories(RecipeDTO recipeDTO, Recipe recipe) {
        assertIterableEquals(
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getId)
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getName)
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeDTO.getCategoryDTOs().stream()
                        .map(CategoryDTO::getVersion)
                        .sorted()
                        .toList(),
                recipe.getCategories().stream()
                        .map(Category::getVersion)
                        .sorted()
                        .toList()
        );
    }

    private static void equalIngredients(RecipeDTO recipeDTO, Recipe recipe) {
        assertIterableEquals(
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getId)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getId)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getName)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getName)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getAmount)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getAmount)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeDTO.getIngredientDTOs().stream()
                        .map(IngredientDTO::getVersion)
                        .sorted()
                        .toList(),
                recipe.getIngredients().stream()
                        .map(Ingredient::getVersion)
                        .sorted()
                        .toList()
        );
        assertIterableEquals(
                recipeDTO.getIngredientDTOs().stream()
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
                recipeDTO.getIngredientDTOs().stream()
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
                recipeDTO.getIngredientDTOs().stream()
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
    }
}