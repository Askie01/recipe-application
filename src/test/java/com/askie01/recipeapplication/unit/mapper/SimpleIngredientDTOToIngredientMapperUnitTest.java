package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@DisplayName("SimpleIngredientDTOToIngredientMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleIngredientDTOToIngredientMapperUnitTest {

    private IngredientDTO source;
    private Ingredient target;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private AmountMapper amountMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @Mock
    private MeasureUnitDTOToMeasureUnitMapper measureUnitDTOToMeasureUnitMapper;
    private IngredientDTOToIngredientMapper mapper;

    private LongIdTestComparator idComparator;
    private StringNameTestComparator nameComparator;
    private AmountTestComparator amountComparator;
    private LongVersionTestComparator versionComparator;
    private MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;
    private IngredientIngredientDTOTestComparator ingredientComparator;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleIngredientDTOToIngredientMapper(
                idMapper,
                nameMapper,
                amountMapper,
                measureUnitDTOToMeasureUnitMapper,
                versionMapper);
        final Faker faker = new Faker();
        final MeasureUnitDTOTestFactory measureUnitDTOTestFactory = new RandomMeasureUnitDTOTestFactory(faker);
        final MeasureUnitTestFactory measureUnitTestFactory = new RandomMeasureUnitTestFactory(faker);
        this.source = new RandomIngredientDTOTestFactory(faker, measureUnitDTOTestFactory).createIngredientDTO();
        this.target = new RandomIngredientTestFactory(faker, measureUnitTestFactory).createIngredient();

        this.idComparator = new LongIdValueTestComparator();
        this.nameComparator = new StringNameValueTestComparator();
        this.amountComparator = new AmountValueTestComparator();
        this.versionComparator = new LongVersionValueTestComparator();
        this.measureUnitComparator = new MeasureUnitMeasureUnitDTOValueTestComparator(
                idComparator,
                nameComparator,
                versionComparator);
        this.ingredientComparator = new IngredientIngredientDTOValueTestComparator(
                idComparator,
                nameComparator,
                amountComparator,
                measureUnitComparator,
                versionComparator);
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is present")
    void map_whenSourceIsPresent_mapsSourceAmountToTargetAmount() {
        doAnswer(invocation -> {
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalAmount = amountComparator.compare(source, target);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("map method should map source measureUnitDTO to target measureUnit when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitDTOToTargetMeasureUnit() {
        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();

        doAnswer(invocation -> {
            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper)
                .map(measureUnitDTO, measureUnit);

        mapper.map(source, target);
        final boolean equalMeasureUnit = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnit);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(source, target);
        mapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map all common fields from source to target when source is present")
    void map_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToTarget() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(source, target);

        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(nameMapper).map(source, target);

        doAnswer(invocation -> {
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(source, target);

        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = target.getMeasureUnit();

        doAnswer(invocation -> {
            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper)
                .map(measureUnitDTO, measureUnit);

        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(source, target);

        mapper.map(source, target);
        final boolean equalIngredients = ingredientComparator.compare(target, source);
        assertTrue(equalIngredients);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Ingredient.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(IngredientDTO.class), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new ingredient id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewIngredientIdAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOId = ingredientDTO.getId();
            ingredient.setId(ingredientDTOId);
            return null;
        }).when(idMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, ingredient);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new ingredient name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewIngredientNameAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final String ingredientDTOName = ingredientDTO.getName();
            ingredient.setName(ingredientDTOName);
            return null;
        }).when(nameMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, ingredient);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source amount to new ingredient amount and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceAmountToNewIngredientAmountAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Double ingredientDTOAmount = ingredientDTO.getAmount();
            ingredient.setAmount(ingredientDTOAmount);
            return null;
        }).when(amountMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalAmount = amountComparator.compare(source, ingredient);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("mapToEntity method should map source measureUnitDTO to new ingredientMeasureUnit and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceMeasureUnitDTOToNewIngredientMeasureUnitAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();

            final MeasureUnit measureUnit = invocation.getArgument(1);
            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class));

        final MeasureUnitDTO measureUnitDTO = source.getMeasureUnitDTO();
        final MeasureUnit measureUnit = mapper.mapToEntity(source).getMeasureUnit();
        final boolean equalMeasureUnits = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new ingredient version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewIngredientVersionAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOVersion = ingredientDTO.getVersion();
            ingredient.setVersion(ingredientDTOVersion);
            return null;
        }).when(versionMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));
        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, ingredient);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map all common fields from source to new ingredient and return it")
    void mapToEntity_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientAndReturnIt() {
        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOId = ingredientDTO.getId();
            ingredient.setId(ingredientDTOId);
            return null;
        }).when(idMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final String ingredientDTOName = ingredientDTO.getName();
            ingredient.setName(ingredientDTOName);
            return null;
        }).when(nameMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Double ingredientDTOAmount = ingredientDTO.getAmount();
            ingredient.setAmount(ingredientDTOAmount);
            return null;
        }).when(amountMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);

            final Long measureUnitDTOId = measureUnitDTO.getId();
            final String measureUnitDTOName = measureUnitDTO.getName();
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();

            measureUnit.setId(measureUnitDTOId);
            measureUnit.setName(measureUnitDTOName);
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(measureUnitDTOToMeasureUnitMapper).map(
                any(MeasureUnitDTO.class),
                any(MeasureUnit.class));

        doAnswer(invocation -> {
            final IngredientDTO ingredientDTO = invocation.getArgument(0);
            final Ingredient ingredient = invocation.getArgument(1);
            final Long ingredientDTOVersion = ingredientDTO.getVersion();
            ingredient.setVersion(ingredientDTOVersion);
            return null;
        }).when(versionMapper).map(
                any(IngredientDTO.class),
                any(Ingredient.class));

        final Ingredient ingredient = mapper.mapToEntity(source);
        final boolean equalIngredients = ingredientComparator.compare(ingredient, source);
        assertTrue(equalIngredients);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(Ingredient.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}