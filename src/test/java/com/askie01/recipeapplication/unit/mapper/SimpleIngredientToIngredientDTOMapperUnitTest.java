package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.mapper.*;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.value.HasAmount;
import com.askie01.recipeapplication.model.value.HasLongId;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import com.askie01.recipeapplication.model.value.HasStringName;
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
@DisplayName("SimpleIngredientToIngredientDTOMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleIngredientToIngredientDTOMapperUnitTest {

    private Ingredient source;
    private IngredientDTO target;
    private IngredientToIngredientDTOMapper mapper;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private AmountMapper amountMapper;

    @Mock
    private LongVersionMapper versionMapper;

    @Mock
    private MeasureUnitToMeasureUnitDTOMapper measureUnitToMeasureUnitDTOMapper;
    private LongIdTestComparator idComparator;
    private StringNameTestComparator nameComparator;
    private AmountTestComparator amountComparator;
    private LongVersionTestComparator versionComparator;
    private MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;
    private IngredientIngredientDTOTestComparator ingredientComparator;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleIngredientToIngredientDTOMapper(
                idMapper,
                nameMapper,
                amountMapper,
                measureUnitToMeasureUnitDTOMapper,
                versionMapper);
        final Faker faker = new Faker();
        final MeasureUnitDTOTestFactory measureUnitDTOTestFactory = new RandomMeasureUnitDTOTestFactory(faker);
        final MeasureUnitTestFactory measureUnitTestFactory = new RandomMeasureUnitTestFactory(faker);
        this.source = new RandomIngredientTestFactory(faker, measureUnitTestFactory).createIngredient();
        this.target = new RandomIngredientDTOTestFactory(faker, measureUnitDTOTestFactory).createIngredientDTO();

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
            final HasLongId source = invocation.getArgument(0);
            final HasLongId target = invocation.getArgument(1);
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(idMapper).map(
                any(HasLongId.class),
                any(HasLongId.class)
        );
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
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
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is present")
    void map_whenSourceIsPresent_mapsSourceAmountToTargetAmount() {
        doAnswer(invocation -> {
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );
        mapper.map(source, target);
        final boolean equalAmount = amountComparator.compare(source, target);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("map method should map source measureUnit to target measureUnitDTO when source is present")
    void map_whenSourceIsPresent_mapsSourceMeasureUnitToTargetMeasureUnitDTO() {
        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = target.getMeasureUnitDTO();

        doAnswer(invocation -> {
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceMeasureUnitId = source.getId();
            final String sourceMeasureUnitName = source.getName();
            final Long sourceMeasureUnitVersion = source.getVersion();
            target.setId(sourceMeasureUnitId);
            target.setName(sourceMeasureUnitName);
            target.setVersion(sourceMeasureUnitVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
        );

        mapper.map(source, target);
        final boolean equalMeasureUnit = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnit);
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
        mapper.map(source, target);
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
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );

        doAnswer(invocation -> {
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceMeasureUnitId = source.getId();
            final String sourceMeasureUnitName = source.getName();
            final Long sourceMeasureUnitVersion = source.getVersion();
            target.setId(sourceMeasureUnitId);
            target.setName(sourceMeasureUnitName);
            target.setVersion(sourceMeasureUnitVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
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

        mapper.map(source, target);
        final boolean equalIngredients = ingredientComparator.compare(source, target);
        assertTrue(equalIngredients);
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
    @DisplayName("mapToDTO method should map source id to new ingredientDTO id and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceIdToNewIngredientDTOIdAndReturnIt() {
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalId = idComparator.compare(ingredientDTO, source);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToDTO method should map source name to new ingredientDTO name and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceNameToNewIngredientDTONameAndReturnIt() {
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalName = nameComparator.compare(ingredientDTO, source);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToDTO method should map source amount to new ingredientDTO amount and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceAmountToNewIngredientDTOAmountAndReturnIt() {
        doAnswer(invocation -> {
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalAmount = amountComparator.compare(ingredientDTO, source);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("mapToDTO method should map source measureUnit to new ingredientDTOMeasureUnitDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceMeasureUnitToNewIngredientDTOMeasureUnitDTOAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceMeasureUnitId = source.getId();
            final String sourceMeasureUnitName = source.getName();
            final Long sourceMeasureUnitVersion = source.getVersion();
            target.setId(sourceMeasureUnitId);
            target.setName(sourceMeasureUnitName);
            target.setVersion(sourceMeasureUnitVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
        );

        final MeasureUnit measureUnit = source.getMeasureUnit();
        final MeasureUnitDTO measureUnitDTO = mapper.mapToDTO(source).getMeasureUnitDTO();
        final boolean equalMeasureUnits = measureUnitComparator.compare(measureUnit, measureUnitDTO);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("mapToDTO method should map source version to new ingredientDTO version and return it")
    void mapToDTO_whenSourceIsPresent_mapsSourceVersionToNewIngredientDTOVersionAndReturnIt() {
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
        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalVersion = versionComparator.compare(ingredientDTO, source);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToDTO method should map all common fields from source to new ingredientDTO and return it")
    void mapToDTO_whenSourceIsPresent_mapsAllCommonFieldsFromSourceToNewIngredientDTOAndReturnIt() {
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
            final HasAmount source = invocation.getArgument(0);
            final HasAmount target = invocation.getArgument(1);
            final Double sourceAmount = source.getAmount();
            target.setAmount(sourceAmount);
            return null;
        }).when(amountMapper).map(
                any(HasAmount.class),
                any(HasAmount.class)
        );

        doAnswer(invocation -> {
            final MeasureUnit source = invocation.getArgument(0);
            final MeasureUnitDTO target = invocation.getArgument(1);

            final Long sourceMeasureUnitId = source.getId();
            final String sourceMeasureUnitName = source.getName();
            final Long sourceMeasureUnitVersion = source.getVersion();
            target.setId(sourceMeasureUnitId);
            target.setName(sourceMeasureUnitName);
            target.setVersion(sourceMeasureUnitVersion);
            return null;
        }).when(measureUnitToMeasureUnitDTOMapper).map(
                any(MeasureUnit.class),
                any(MeasureUnitDTO.class)
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

        final IngredientDTO ingredientDTO = mapper.mapToDTO(source);
        final boolean equalIngredients = ingredientComparator.compare(source, ingredientDTO);
        assertTrue(equalIngredients);
    }

    @Test
    @DisplayName("mapToDTO method should throw NullPointerException when source is null")
    void mapToDTO_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(HasLongId.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToDTO(null));
    }
}