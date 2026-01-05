package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.mapper.*;
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
@DisplayName("SimpleMeasureUnitDTOToMeasureUnitMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleMeasureUnitDTOToMeasureUnitMapperUnitTest {

    private MeasureUnitDTO source;
    private MeasureUnit target;

    @Mock
    private LongIdMapper idMapper;

    @Mock
    private StringNameMapper nameMapper;

    @Mock
    private LongVersionMapper versionMapper;
    private MeasureUnitDTOToMeasureUnitMapper mapper;

    private LongIdTestComparator idComparator;
    private StringNameTestComparator nameComparator;
    private LongVersionTestComparator versionComparator;
    private MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleMeasureUnitDTOToMeasureUnitMapper(idMapper, nameMapper, versionMapper);
        final Faker faker = new Faker();
        this.source = new RandomMeasureUnitDTOTestFactory(faker).createMeasureUnitDTO();
        this.target = new RandomMeasureUnitTestFactory(faker).createMeasureUnit();

        this.idComparator = new LongIdValueTestComparator();
        this.nameComparator = new StringNameValueTestComparator();
        this.versionComparator = new LongVersionValueTestComparator();
        this.measureUnitComparator = new MeasureUnitMeasureUnitDTOValueTestComparator(
                idComparator,
                nameComparator,
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
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
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
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(versionMapper).map(source, target);

        mapper.map(source, target);
        final boolean equalMeasureUnits = measureUnitComparator.compare(target, source);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(MeasureUnit.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(any(MeasureUnitDTO.class), isNull());
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }

    @Test
    @DisplayName("mapToEntity method should map source id to new MeasureUnit id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewMeasureUnitIdAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final Long measureUnitDTOId = measureUnitDTO.getId();
            measureUnit.setId(measureUnitDTOId);
            return null;
        }).when(idMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalId = idComparator.compare(source, measureUnit);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new MeasureUnit name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewMeasureUnitNameAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final String measureUnitDTOName = measureUnitDTO.getName();
            measureUnit.setName(measureUnitDTOName);
            return null;
        }).when(nameMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, measureUnit);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new MeasureUnit version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewMeasureUnitVersionAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(versionMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, measureUnit);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source id, name and version to new MeasureUnit id, name and version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdNameVersionToNewMeasureUnitIdNameVersionAndReturnIt() {
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final Long measureUnitDTOId = measureUnitDTO.getId();
            measureUnit.setId(measureUnitDTOId);
            return null;
        }).when(idMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final String measureUnitDTOName = measureUnitDTO.getName();
            measureUnit.setName(measureUnitDTOName);
            return null;
        }).when(nameMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(versionMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));

        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalMeasureUnits = measureUnitComparator.compare(measureUnit, source);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(idMapper)
                .map(isNull(), any(MeasureUnit.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}