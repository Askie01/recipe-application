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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimpleRecipeToRecipeDTOMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleRecipeToRecipeDTOMapperUnitTest {

    private Recipe source;
    private RecipeDTO target;
    private RecipeToRecipeDTOMapper recipeMapper;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private DescriptionMapper descriptionMapper;

    @Mock
    private DifficultyToDifficultyDTOMapper difficultyMapper;

    @Mock
    private CategoryToCategoryDTOMapper categoryMapper;

    @Mock
    private IngredientToIngredientDTOMapper ingredientMapper;

    @Mock
    private ServingsMapper servingsMapper;

    @Mock
    private CookingTimeMapper cookingTimeMapper;

    @Mock
    private InstructionsMapper instructionsMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @BeforeEach
    void setUp() {
        this.source = getTestRecipe();
        this.target = getTestRecipeDTO();
        this.recipeMapper = new SimpleRecipeToRecipeDTOMapper(
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
    @DisplayName("map method should map source difficulty to target difficultyDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceDifficultyToTargetDifficultyDTO() {
        when(difficultyMapper.mapToValue(any(Difficulty.class)))
                .thenAnswer(invocation -> {
                    final Difficulty difficulty = invocation.getArgument(0);
                    final String difficultyName = difficulty.name();
                    return DifficultyDTO.valueOf(difficultyName);
                });
        recipeMapper.map(source, target);
        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = target.getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
    }

    @Test
    @DisplayName("map method should map source categories to target categoryDTOs when source is present")
    void map_whenSourceIsPresent_mapsSourceCategoriesToTargetCategoryDTOs() {
        when(categoryMapper.mapToDTO(any(Category.class)))
                .thenAnswer(invocation -> {
                    final Category category = invocation.getArgument(0);
                    final Long categoryId = category.getId();
                    final String categoryName = category.getName();
                    final Long categoryVersion = category.getVersion();
                    return CategoryDTO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .version(categoryVersion)
                            .build();
                });
        recipeMapper.map(source, target);
        equalCategories(source, target);
    }

    @Test
    @DisplayName("map method should map source ingredients to target ingredientDTOs when source is present")
    void map_whenSourceIsPresent_mapsSourceIngredientsToTargetIngredientDTOs() {
        when(ingredientMapper.mapToDTO(any(Ingredient.class)))
                .thenAnswer(invocation -> {
                    final Ingredient ingredient = invocation.getArgument(0);
                    final Long ingredientId = ingredient.getId();
                    final String ingredientName = ingredient.getName();
                    final Double ingredientAmount = ingredient.getAmount();
                    final Long ingredientVersion = ingredient.getVersion();

                    final MeasureUnit measureUnit = ingredient.getMeasureUnit();
                    final Long measureUnitId = measureUnit.getId();
                    final String measureUnitName = measureUnit.getName();
                    final Long measureUnitVersion = measureUnit.getVersion();
                    final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                            .id(measureUnitId)
                            .name(measureUnitName)
                            .version(measureUnitVersion)
                            .build();

                    return IngredientDTO.builder()
                            .id(ingredientId)
                            .name(ingredientName)
                            .amount(ingredientAmount)
                            .measureUnitDTO(measureUnitDTO)
                            .version(ingredientVersion)
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
        when(difficultyMapper.mapToValue(any(Difficulty.class)))
                .thenAnswer(invocation -> {
                    final Difficulty difficulty = invocation.getArgument(0);
                    final String difficultyName = difficulty.name();
                    return DifficultyDTO.valueOf(difficultyName);
                });
        when(categoryMapper.mapToDTO(any(Category.class)))
                .thenAnswer(invocation -> {
                    final Category category = invocation.getArgument(0);
                    final Long categoryId = category.getId();
                    final String categoryName = category.getName();
                    final Long categoryVersion = category.getVersion();
                    return CategoryDTO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .version(categoryVersion)
                            .build();
                });
        when(ingredientMapper.mapToDTO(any(Ingredient.class)))
                .thenAnswer(invocation -> {
                    final Ingredient ingredient = invocation.getArgument(0);
                    final Long ingredientId = ingredient.getId();
                    final String ingredientName = ingredient.getName();
                    final Double ingredientAmount = ingredient.getAmount();
                    final Long ingredientVersion = ingredient.getVersion();

                    final MeasureUnit measureUnit = ingredient.getMeasureUnit();
                    final Long measureUnitId = measureUnit.getId();
                    final String measureUnitName = measureUnit.getName();
                    final Long measureUnitVersion = measureUnit.getVersion();
                    final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                            .id(measureUnitId)
                            .name(measureUnitName)
                            .version(measureUnitVersion)
                            .build();

                    return IngredientDTO.builder()
                            .id(ingredientId)
                            .name(ingredientName)
                            .amount(ingredientAmount)
                            .measureUnitDTO(measureUnitDTO)
                            .version(ingredientVersion)
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

        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = target.getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
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
                .map(isNull(), any());
        assertThrows(NullPointerException.class, () -> recipeMapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(), isNull());
        assertThrows(NullPointerException.class, () -> recipeMapper.map(source, null));
    }

    @Test
    @DisplayName("mapToDTO method should map source id to new recipeDTO id when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewRecipeDTOIdAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final Long sourceId = source.getId();
        final Long recipeDTOId = recipeDTO.getId();
        assertEquals(sourceId, recipeDTOId);
    }

    @Test
    @DisplayName("mapToDTO method should map source image to new recipeDTO image when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceImageToNewRecipeDTOImageAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final byte[] sourceImage = source.getImage();
        final byte[] recipeDTOImage = recipeDTO.getImage();
        assertArrayEquals(sourceImage, recipeDTOImage);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new recipeDTO name when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewRecipeDTONameAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final String sourceName = source.getName();
        final String recipeDTOName = recipeDTO.getName();
        assertEquals(sourceName, recipeDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source description to new recipeDTO description when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDTODescriptionAndReturnThatNewRecipeObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final String sourceDescription = source.getDescription();
        final String recipeDTODescription = recipeDTO.getDescription();
        assertEquals(sourceDescription, recipeDTODescription);
    }

    @Test
    @DisplayName("mapToDTO method should map source difficultyDTO to new recipeDTO difficultyDTO when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceDifficultyDTOToNewRecipeDTODifficultyDTOAndReturnThatNewRecipeDTOObject() {
        when(difficultyMapper.mapToValue(any(Difficulty.class)))
                .thenAnswer(invocation -> {
                    final Difficulty difficulty = invocation.getArgument(0);
                    final String difficultyName = difficulty.name();
                    return DifficultyDTO.valueOf(difficultyName);
                });
        final String difficultyName = source.getDifficulty().name();
        final String difficultyDTOName = recipeMapper.mapToDTO(source).getDifficultyDTO().name();
        assertEquals(difficultyName, difficultyDTOName);
    }

    @Test
    @DisplayName("mapToDTO method should map source categories to new recipeDTO categoryDTOs when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceCategoriesToNewRecipeDTOCategoryDTOsAndReturnThatNewRecipeDTOObject() {
        when(categoryMapper.mapToDTO(any(Category.class)))
                .thenAnswer(invocation -> {
                    final Category category = invocation.getArgument(0);
                    final Long categoryId = category.getId();
                    final String categoryName = category.getName();
                    final Long categoryVersion = category.getVersion();
                    return CategoryDTO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .version(categoryVersion)
                            .build();
                });
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        equalCategories(source, recipeDTO);
    }

    @Test
    @DisplayName("mapToDTO method should map source ingredients to new recipeDTO ingredientDTOs when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceIngredientsToNewRecipeDTOIngredientDTOsAndReturnThatNewRecipeDTOObject() {
        when(ingredientMapper.mapToDTO(any(Ingredient.class)))
                .thenAnswer(invocation -> {
                    final Ingredient ingredient = invocation.getArgument(0);
                    final Long ingredientId = ingredient.getId();
                    final String ingredientName = ingredient.getName();
                    final Double ingredientAmount = ingredient.getAmount();
                    final Long ingredientVersion = ingredient.getVersion();

                    final MeasureUnit measureUnit = ingredient.getMeasureUnit();
                    final Long measureUnitId = measureUnit.getId();
                    final String measureUnitName = measureUnit.getName();
                    final Long measureUnitVersion = measureUnit.getVersion();
                    final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                            .id(measureUnitId)
                            .name(measureUnitName)
                            .version(measureUnitVersion)
                            .build();

                    return IngredientDTO.builder()
                            .id(ingredientId)
                            .name(ingredientName)
                            .amount(ingredientAmount)
                            .measureUnitDTO(measureUnitDTO)
                            .version(ingredientVersion)
                            .build();
                });
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        equalIngredients(source, recipeDTO);
    }

    @Test
    @DisplayName("mapToDTO method should map source servings to new recipeDTO servings when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceServingsToNewRecipeDTOServingsAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final Double sourceServings = source.getServings();
        final Double recipeDTOServings = recipeDTO.getServings();
        assertEquals(sourceServings, recipeDTOServings);
    }

    @Test
    @DisplayName("mapToDTO method should map source cookingTime to new recipeDTO cookingTime when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeDTOCookingTimeAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final Integer sourceCookingTime = source.getCookingTime();
        final Integer recipeDTOCookingTime = recipeDTO.getCookingTime();
        assertEquals(sourceCookingTime, recipeDTOCookingTime);
    }

    @Test
    @DisplayName("mapToDTO method should map source instructions to new recipeDTO instructions when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeDTOInstructionsAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final String sourceInstructions = source.getInstructions();
        final String recipeDTOInstructions = recipeDTO.getInstructions();
        assertEquals(sourceInstructions, recipeDTOInstructions);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new recipeDTO version when source is present and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewRecipeDTOVersionAndReturnThatNewRecipeDTOObject() {
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
        final Long sourceVersion = source.getVersion();
        final Long recipeDTOVersion = recipeDTO.getVersion();
        assertEquals(sourceVersion, recipeDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new RecipeDTO and return that new RecipeDTO object")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeDTOAndReturnThatNewRecipeDTOObject() {
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
        when(difficultyMapper.mapToValue(any(Difficulty.class)))
                .thenAnswer(invocation -> {
                    final Difficulty difficulty = invocation.getArgument(0);
                    final String difficultyName = difficulty.name();
                    return DifficultyDTO.valueOf(difficultyName);
                });
        when(categoryMapper.mapToDTO(any(Category.class)))
                .thenAnswer(invocation -> {
                    final Category category = invocation.getArgument(0);
                    final Long categoryId = category.getId();
                    final String categoryName = category.getName();
                    final Long categoryVersion = category.getVersion();
                    return CategoryDTO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .version(categoryVersion)
                            .build();
                });
        when(ingredientMapper.mapToDTO(any(Ingredient.class)))
                .thenAnswer(invocation -> {
                    final Ingredient ingredient = invocation.getArgument(0);
                    final Long ingredientId = ingredient.getId();
                    final String ingredientName = ingredient.getName();
                    final Double ingredientAmount = ingredient.getAmount();
                    final Long ingredientVersion = ingredient.getVersion();

                    final MeasureUnit measureUnit = ingredient.getMeasureUnit();
                    final Long measureUnitId = measureUnit.getId();
                    final String measureUnitName = measureUnit.getName();
                    final Long measureUnitVersion = measureUnit.getVersion();
                    final MeasureUnitDTO measureUnitDTO = MeasureUnitDTO.builder()
                            .id(measureUnitId)
                            .name(measureUnitName)
                            .version(measureUnitVersion)
                            .build();

                    return IngredientDTO.builder()
                            .id(ingredientId)
                            .name(ingredientName)
                            .amount(ingredientAmount)
                            .measureUnitDTO(measureUnitDTO)
                            .version(ingredientVersion)
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
        final RecipeDTO recipeDTO = recipeMapper.mapToDTO(source);
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

        final Double sourceServings = source.getServings();
        final Double recipeDTOServings = recipeDTO.getServings();
        assertEquals(sourceServings, recipeDTOServings);

        final Integer sourceCookingTime = source.getCookingTime();
        final Integer recipeDTOCookingTime = recipeDTO.getCookingTime();
        assertEquals(sourceCookingTime, recipeDTOCookingTime);

        final String sourceInstructions = source.getInstructions();
        final String recipeDTOInstructions = recipeDTO.getInstructions();
        assertEquals(sourceInstructions, recipeDTOInstructions);

        final Long sourceVersion = source.getVersion();
        final Long recipeDTOVersion = recipeDTO.getVersion();
        assertEquals(sourceVersion, recipeDTOVersion);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any());
        assertThrows(NullPointerException.class, () -> recipeMapper.mapToDTO(null));
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