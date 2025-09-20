package com.askie01.recipeapplication.unit.mapper;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("SimpleMeasureUnitDTOToMeasureUnitMapper unit tests")
class SimpleMeasureUnitDTOToMeasureUnitMapperUnitTest {

    @Mock
    private LongIdMapper longIdMapper;

    @Mock
    private StringNameMapper stringNameMapper;

    @Mock
    private LongVersionMapper longVersionMapper;
    private MeasureUnitDTOToMeasureUnitMapper mapper;
    private MeasureUnitDTO source;
    private MeasureUnit target;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleMeasureUnitDTOToMeasureUnitMapper(longIdMapper, stringNameMapper, longVersionMapper);
        final Faker faker = new Faker();
        this.source = new RandomMeasureUnitDTOTestFactory(faker).createMeasureUnitDTO();
        this.target = new RandomMeasureUnitTestFactory(faker).createMeasureUnit();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(longIdMapper).map(source, target);
        mapper.map(source, target);
        final Long sourceId = source.getId();
        final Long targetId = target.getId();
        assertEquals(sourceId, targetId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(stringNameMapper).map(source, target);
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(longVersionMapper).map(source, target);
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
        doAnswer(invocation -> {
            final Long sourceId = source.getId();
            target.setId(sourceId);
            return null;
        }).when(longIdMapper).map(source, target);
        doAnswer(invocation -> {
            final String sourceName = source.getName();
            target.setName(sourceName);
            return null;
        }).when(stringNameMapper).map(source, target);
        doAnswer(invocation -> {
            final Long sourceVersion = source.getVersion();
            target.setVersion(sourceVersion);
            return null;
        }).when(longVersionMapper).map(source, target);

        mapper.map(source, target);
        final Long sourceId = source.getId();
        final String sourceName = source.getName();
        final Long sourceVersion = source.getVersion();
        final Long targetId = target.getId();
        final String targetName = target.getName();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceId, targetId);
        assertEquals(sourceName, targetName);
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(isNull(), any(MeasureUnit.class));
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
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
        }).when(longIdMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long measureUnitId = measureUnit.getId();
        assertEquals(sourceId, measureUnitId);
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
        }).when(stringNameMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String measureUnitName = measureUnit.getName();
        assertEquals(sourceName, measureUnitName);
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
        }).when(longVersionMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(sourceVersion, measureUnitVersion);
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
        }).when(longIdMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final String measureUnitDTOName = measureUnitDTO.getName();
            measureUnit.setName(measureUnitDTOName);
            return null;
        }).when(stringNameMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));
        doAnswer(invocation -> {
            final MeasureUnitDTO measureUnitDTO = invocation.getArgument(0);
            final MeasureUnit measureUnit = invocation.getArgument(1);
            final Long measureUnitDTOVersion = measureUnitDTO.getVersion();
            measureUnit.setVersion(measureUnitDTOVersion);
            return null;
        }).when(longVersionMapper).map(any(MeasureUnitDTO.class), any(MeasureUnit.class));

        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final String sourceName = source.getName();
        final Long sourceVersion = source.getVersion();
        final Long measureUnitId = measureUnit.getId();
        final String measureUnitName = measureUnit.getName();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(sourceId, measureUnitId);
        assertEquals(sourceName, measureUnitName);
        assertEquals(sourceVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        doThrow(NullPointerException.class)
                .when(longIdMapper)
                .map(isNull(), any(MeasureUnit.class));
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}