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
        this.source = getRecipeDTOTestFactory().createRecipeDTO();
        this.target = getRecipeTestFactory().createRecipe();

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
            final HasLongId sourceHasLongId = invocation.getArgument(0);
            final HasLongId targetHasLongId = invocation.getArgument(1);
            final Long sourceId = sourceHasLongId.getId();
            targetHasLongId.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class));
        recipeMapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should map source image to target image when source is present")
    void map_whenSourceIsPresent_mapsSourceImageToTargetImage() {
        doAnswer(invocation -> {
            final HasImage sourceHasImage = invocation.getArgument(0);
            final HasImage targetHasImage = invocation.getArgument(1);
            final byte[] sourceImage = sourceHasImage.getImage();
            targetHasImage.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class));
        recipeMapper.map(source, target);
        final boolean equalImage = imageComparator.compare(source, target);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final HasStringName sourceHasStringName = invocation.getArgument(0);
            final HasStringName targetHasStringName = invocation.getArgument(1);
            final String sourceName = sourceHasStringName.getName();
            targetHasStringName.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class));
        recipeMapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source description to target description when source is present")
    void map_whenSourceIsPresent_mapsSourceDescriptionToTargetDescription() {
        doAnswer(invocation -> {
            final HasDescription sourceHasDescription = invocation.getArgument(0);
            final HasDescription targetHasDescription = invocation.getArgument(1);
            final String sourceDescription = sourceHasDescription.getDescription();
            targetHasDescription.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class));
        recipeMapper.map(source, target);
        final boolean equalDescription = descriptionComparator.compare(source, target);
        assertTrue(equalDescription);
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
        final DifficultyDTO difficultyDTO = source.getDifficultyDTO();
        final Difficulty difficulty = target.getDifficulty();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
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
        doAnswer(invocation -> {
            final HasServings sourceHasServings = invocation.getArgument(0);
            final HasServings targetHasServings = invocation.getArgument(1);
            final Double sourceServings = sourceHasServings.getServings();
            targetHasServings.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class));
        recipeMapper.map(source, target);
        final boolean equalServings = servingsComparator.compare(source, target);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("map method should map source cookingTime to target cookingTime when source is present")
    void map_whenSourceIsPresent_mapsSourceCookingTimeToTargetCookingTime() {
        doAnswer(invocation -> {
            final HasCookingTime sourceHasCookingTime = invocation.getArgument(0);
            final HasCookingTime targetHasCookingTime = invocation.getArgument(1);
            final Integer sourceCookingTime = sourceHasCookingTime.getCookingTime();
            targetHasCookingTime.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class));
        recipeMapper.map(source, target);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, target);
        assertTrue(equalCookingTime);
    }

    @Test
    @DisplayName("map method should map source instructions to target instructions when source is present")
    void map_whenSourceIsPresent_mapsSourceInstructionsToTargetInstructions() {
        doAnswer(invocation -> {
            final HasInstructions sourceHasInstructions = invocation.getArgument(0);
            final HasInstructions targetHasInstructions = invocation.getArgument(1);
            final String sourceInstructions = sourceHasInstructions.getInstructions();
            targetHasInstructions.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class));
        recipeMapper.map(source, target);
        final boolean equalInstructions = instructionsComparator.compare(source, target);
        assertTrue(equalInstructions);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final HasLongVersion sourceHasLongVersion = invocation.getArgument(0);
            final HasLongVersion targetHasLongVersion = invocation.getArgument(1);
            final Long sourceVersion = sourceHasLongVersion.getVersion();
            targetHasLongVersion.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class));
        recipeMapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        doAnswer(invocation -> {
            final HasLongId sourceHasLongId = invocation.getArgument(0);
            final HasLongId targetHasLongId = invocation.getArgument(1);
            final Long sourceId = sourceHasLongId.getId();
            targetHasLongId.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class));

        doAnswer(invocation -> {
            final HasImage sourceHasImage = invocation.getArgument(0);
            final HasImage targetHasImage = invocation.getArgument(1);
            final byte[] sourceImage = sourceHasImage.getImage();
            targetHasImage.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class));

        doAnswer(invocation -> {
            final HasStringName sourceHasStringName = invocation.getArgument(0);
            final HasStringName targetHasStringName = invocation.getArgument(1);
            final String sourceName = sourceHasStringName.getName();
            targetHasStringName.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class));

        doAnswer(invocation -> {
            final HasDescription sourceHasDescription = invocation.getArgument(0);
            final HasDescription targetHasDescription = invocation.getArgument(1);
            final String sourceDescription = sourceHasDescription.getDescription();
            targetHasDescription.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class));

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
            final HasServings sourceHasServings = invocation.getArgument(0);
            final HasServings targetHasServings = invocation.getArgument(1);
            final Double sourceServings = sourceHasServings.getServings();
            targetHasServings.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class));

        doAnswer(invocation -> {
            final HasCookingTime sourceHasCookingTime = invocation.getArgument(0);
            final HasCookingTime targetHasCookingTime = invocation.getArgument(1);
            final Integer sourceCookingTime = sourceHasCookingTime.getCookingTime();
            targetHasCookingTime.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class));

        doAnswer(invocation -> {
            final HasInstructions sourceHasInstructions = invocation.getArgument(0);
            final HasInstructions targetHasInstructions = invocation.getArgument(1);
            final String sourceInstructions = sourceHasInstructions.getInstructions();
            targetHasInstructions.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class));

        doAnswer(invocation -> {
            final HasLongVersion sourceHasLongVersion = invocation.getArgument(0);
            final HasLongVersion targetHasLongVersion = invocation.getArgument(1);
            final Long sourceVersion = sourceHasLongVersion.getVersion();
            targetHasLongVersion.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class));

        recipeMapper.map(source, target);
        final boolean equalRecipes = recipeComparator.compare(target, source);
        assertTrue(equalRecipes);
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
            final HasLongId sourceHasLongId = invocation.getArgument(0);
            final HasLongId targetHasLongId = invocation.getArgument(1);
            final Long sourceId = sourceHasLongId.getId();
            targetHasLongId.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, recipe);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source image to new recipe image when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceImageToNewRecipeImageAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasImage sourceHasImage = invocation.getArgument(0);
            final HasImage targetHasImage = invocation.getArgument(1);
            final byte[] sourceImage = sourceHasImage.getImage();
            targetHasImage.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalImage = imageComparator.compare(source, recipe);
        assertTrue(equalImage);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new recipe name when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewRecipeNameAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasStringName sourceHasStringName = invocation.getArgument(0);
            final HasStringName targetHasStringName = invocation.getArgument(1);
            final String sourceName = sourceHasStringName.getName();
            targetHasStringName.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, recipe);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source description to new recipe description when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceDescriptionToNewRecipeDescriptionAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasDescription sourceHasDescription = invocation.getArgument(0);
            final HasDescription targetHasDescription = invocation.getArgument(1);
            final String sourceDescription = sourceHasDescription.getDescription();
            targetHasDescription.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalDescription = descriptionComparator.compare(source, recipe);
        assertTrue(equalDescription);
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
        final Difficulty difficulty = recipeMapper.mapToEntity(source).getDifficulty();
        final DifficultyDTO difficultyDTO = source.getDifficultyDTO();
        final boolean equalDifficulties = difficultyComparator.compare(difficulty, difficultyDTO);
        assertTrue(equalDifficulties);
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
        doAnswer(invocation -> {
            final HasServings sourceHasServings = invocation.getArgument(0);
            final HasServings targetHasServings = invocation.getArgument(1);
            final Double sourceServings = sourceHasServings.getServings();
            targetHasServings.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalServings = servingsComparator.compare(source, recipe);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("mapToEntity method should map source cookingTime to new recipe cookingTime when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceCookingTimeToNewRecipeCookingTimeAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasCookingTime sourceHasCookingTime = invocation.getArgument(0);
            final HasCookingTime targetHasCookingTime = invocation.getArgument(1);
            final Integer sourceCookingTime = sourceHasCookingTime.getCookingTime();
            targetHasCookingTime.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, recipe);
        assertTrue(equalCookingTime);
    }

    @Test
    @DisplayName("mapToEntity method should map source instructions to new recipe instructions when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceInstructionsToNewRecipeInstructionsAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasInstructions sourceHasInstructions = invocation.getArgument(0);
            final HasInstructions targetHasInstructions = invocation.getArgument(1);
            final String sourceInstructions = sourceHasInstructions.getInstructions();
            targetHasInstructions.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalInstructions = instructionsComparator.compare(source, recipe);
        assertTrue(equalInstructions);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new recipe version when source is present and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewRecipeVersionAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasLongVersion sourceHasLongVersion = invocation.getArgument(0);
            final HasLongVersion targetHasLongVersion = invocation.getArgument(1);
            final Long sourceVersion = sourceHasLongVersion.getVersion();
            targetHasLongVersion.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class));
        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, recipe);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new Recipe and return that new Recipe object")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewRecipeAndReturnThatNewRecipeObject() {
        doAnswer(invocation -> {
            final HasLongId sourceHasLongId = invocation.getArgument(0);
            final HasLongId targetHasLongId = invocation.getArgument(1);
            final Long sourceId = sourceHasLongId.getId();
            targetHasLongId.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class));

        doAnswer(invocation -> {
            final HasImage sourceHasImage = invocation.getArgument(0);
            final HasImage targetHasImage = invocation.getArgument(1);
            final byte[] sourceImage = sourceHasImage.getImage();
            targetHasImage.setImage(sourceImage);
            return null;
        }).when(imageMapper).map(
                any(HasImage.class),
                any(HasImage.class));

        doAnswer(invocation -> {
            final HasStringName sourceHasStringName = invocation.getArgument(0);
            final HasStringName targetHasStringName = invocation.getArgument(1);
            final String sourceName = sourceHasStringName.getName();
            targetHasStringName.setName(sourceName);
            return null;
        }).when(nameMapper).map(
                any(HasStringName.class),
                any(HasStringName.class));

        doAnswer(invocation -> {
            final HasDescription sourceHasDescription = invocation.getArgument(0);
            final HasDescription targetHasDescription = invocation.getArgument(1);
            final String sourceDescription = sourceHasDescription.getDescription();
            targetHasDescription.setDescription(sourceDescription);
            return null;
        }).when(descriptionMapper).map(
                any(HasDescription.class),
                any(HasDescription.class));

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
            final HasServings sourceHasServings = invocation.getArgument(0);
            final HasServings targetHasServings = invocation.getArgument(1);
            final Double sourceServings = sourceHasServings.getServings();
            targetHasServings.setServings(sourceServings);
            return null;
        }).when(servingsMapper).map(
                any(HasServings.class),
                any(HasServings.class));

        doAnswer(invocation -> {
            final HasCookingTime sourceHasCookingTime = invocation.getArgument(0);
            final HasCookingTime targetHasCookingTime = invocation.getArgument(1);
            final Integer sourceCookingTime = sourceHasCookingTime.getCookingTime();
            targetHasCookingTime.setCookingTime(sourceCookingTime);
            return null;
        }).when(cookingTimeMapper).map(
                any(HasCookingTime.class),
                any(HasCookingTime.class));

        doAnswer(invocation -> {
            final HasInstructions sourceHasInstructions = invocation.getArgument(0);
            final HasInstructions targetHasInstructions = invocation.getArgument(1);
            final String sourceInstructions = sourceHasInstructions.getInstructions();
            targetHasInstructions.setInstructions(sourceInstructions);
            return null;
        }).when(instructionsMapper).map(
                any(HasInstructions.class),
                any(HasInstructions.class));

        doAnswer(invocation -> {
            final HasLongVersion sourceHasLongVersion = invocation.getArgument(0);
            final HasLongVersion targetHasLongVersion = invocation.getArgument(1);
            final Long sourceVersion = sourceHasLongVersion.getVersion();
            targetHasLongVersion.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(
                any(HasLongVersion.class),
                any(HasLongVersion.class));

        final Recipe recipe = recipeMapper.mapToEntity(source);
        final boolean equalRecipes = recipeComparator.compare(recipe, source);
        assertTrue(equalRecipes);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(HasLongId.class));
        assertThrows(NullPointerException.class, () -> recipeMapper.mapToEntity(null));
    }
}