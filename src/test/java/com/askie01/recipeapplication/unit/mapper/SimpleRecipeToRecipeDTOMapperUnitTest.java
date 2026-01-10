package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.*;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.model.value.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private LongIdTestComparator idComparator;
    private ImageTestComparator imageComparator;
    private StringNameTestComparator nameComparator;
    private DescriptionTestComparator descriptionComparator;
    private DifficultyDifficultyDTOTestComparator difficultyComparator;
    private ServingsTestComparator servingsComparator;
    private CookingTimeTestComparator cookingTimeComparator;
    private InstructionsTestComparator instructionsComparator;
    private LongVersionTestComparator versionComparator;
    private RecipeRecipeDTOTestComparator recipeComparator;

    @BeforeEach
    void setUp() {
        this.source = getRecipeTestFactory().createRecipe();
        this.target = getRecipeDTOTestFactory().createRecipeDTO();
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
        this.idComparator = new LongIdValueTestComparator();
        this.imageComparator = new ImageValueTestComparator();
        this.nameComparator = new StringNameValueTestComparator();
        this.descriptionComparator = new DescriptionValueTestComparator();
        this.difficultyComparator = new DifficultyDifficultyDTOValueTestComparator();
        this.versionComparator = new LongVersionValueTestComparator();
        this.servingsComparator = new ServingsValueTestComparator();
        this.cookingTimeComparator = new CookingTimeValueTestComparator();
        this.instructionsComparator = new InstructionsValueTestComparator();
        this.recipeComparator = getRecipeComparator();
    }

    private static RecipeDTOTestFactory getRecipeDTOTestFactory() {
        final Faker faker = new Faker();
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

    private static RecipeTestFactory getRecipeTestFactory() {
        final Faker faker = new Faker();
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

    private RecipeRecipeDTOValueTestComparator getRecipeComparator() {
        return new RecipeRecipeDTOValueTestComparator(
                idComparator,
                imageComparator,
                nameComparator,
                descriptionComparator,
                difficultyComparator,
                new CategoryCategoryDTOValueTestComparator(
                        idComparator,
                        nameComparator,
                        versionComparator
                ),
                new IngredientIngredientDTOValueTestComparator(
                        idComparator,
                        nameComparator,
                        new AmountValueTestComparator(),
                        new MeasureUnitMeasureUnitDTOValueTestComparator(
                                idComparator,
                                nameComparator,
                                versionComparator
                        ),
                        versionComparator
                ),
                servingsComparator,
                cookingTimeComparator,
                instructionsComparator,
                versionComparator
        );
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
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
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
        final boolean equalImage = imageComparator.compare(source, target);
        assertTrue(equalImage);
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
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
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
        final boolean equalDescription = descriptionComparator.compare(source, target);
        assertTrue(equalDescription);
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
        final Difficulty difficulty = source.getDifficulty();
        final DifficultyDTO difficultyDTO = target.getDifficultyDTO();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
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
        final boolean equalServings = servingsComparator.compare(source, target);
        assertTrue(equalServings);
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
        final boolean equalCookingTime = cookingTimeComparator.compare(source, target);
        assertTrue(equalCookingTime);
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
        final boolean equalInstructions = instructionsComparator.compare(source, target);
        assertTrue(equalInstructions);
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
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
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
        final boolean equalRecipes = recipeComparator.compare(source, target);
        assertTrue(equalRecipes);
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
        final boolean equalId = idComparator.compare(source, recipeDTO);
        assertTrue(equalId);
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
        final boolean equalImage = imageComparator.compare(source, recipeDTO);
        assertTrue(equalImage);
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
        final boolean equalName = nameComparator.compare(source, recipeDTO);
        assertTrue(equalName);
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
        final boolean equalDescription = descriptionComparator.compare(source, recipeDTO);
        assertTrue(equalDescription);
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
        final DifficultyDTO difficultyDTO = recipeMapper.mapToDTO(source).getDifficultyDTO();
        final Difficulty difficulty = source.getDifficulty();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
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
        final boolean equalServings = servingsComparator.compare(source, recipeDTO);
        assertTrue(equalServings);
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
        final boolean equalCookingTime = cookingTimeComparator.compare(source, recipeDTO);
        assertTrue(equalCookingTime);
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
        final boolean equalInstructions = instructionsComparator.compare(source, recipeDTO);
        assertTrue(equalInstructions);
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
        final boolean equalVersion = versionComparator.compare(source, recipeDTO);
        assertTrue(equalVersion);
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
        final boolean equalRecipes = recipeComparator.compare(source, recipeDTO);
        assertTrue(equalRecipes);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any());
        assertThrows(NullPointerException.class, () -> recipeMapper.mapToDTO(null));
    }
}