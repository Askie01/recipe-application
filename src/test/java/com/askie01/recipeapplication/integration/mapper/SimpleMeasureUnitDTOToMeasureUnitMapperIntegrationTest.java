package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.MeasureUnitMeasureUnitDTOTestComparator;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.mapper.MeasureUnitDTOToMeasureUnitMapper;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
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
        SimpleMeasureUnitDTOToMeasureUnitMapperConfiguration.class,
        ValidatedLongIdMapperConfiguration.class,
        PositiveLongIdValidatorConfiguration.class,
        ValidatedStringNameMapperConfiguration.class,
        NonBlankStringNameValidatorConfiguration.class,
        ValidatedLongVersionMapperConfiguration.class,
        PositiveLongVersionValidatorConfiguration.class,
        RandomMeasureUnitDTOTestFactoryTestConfiguration.class,
        RandomMeasureUnitTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.measureUnitDTO-to-measureUnit-type=simple",
        "component.mapper.id-type=validated-long-id",
        "component.validator.id-type=positive-long-id",
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string",
        "component.mapper.version-type=validated-long-version",
        "component.validator.version-type=positive-long-version"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("SimpleMeasureUnitDTOToMeasureUnitMapper integration tests")
class SimpleMeasureUnitDTOToMeasureUnitMapperIntegrationTest {

    private final MeasureUnitDTOToMeasureUnitMapper mapper;
    private final MeasureUnitMeasureUnitDTOTestComparator comparator;
    private final MeasureUnitDTOTestFactory measureUnitDTOFactory;
    private final MeasureUnitTestFactory measureUnitFactory;
    private MeasureUnitDTO source;
    private MeasureUnit target;

    @BeforeEach
    void setUp() {
        this.source = measureUnitDTOFactory.createMeasureUnitDTO();
        this.target = measureUnitFactory.createMeasureUnit();
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
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
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
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
        mapper.map(source, target);
        final boolean sourceEqualsTarget = comparator.compare(target, source);
        assertTrue(sourceEqualsTarget);
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
    @DisplayName("mapToEntity method should map source id to new MeasureUnit id and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdToNewMeasureUnitIdAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final Long sourceId = source.getId();
        final Long measureUnitId = measureUnit.getId();
        assertEquals(sourceId, measureUnitId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new MeasureUnit name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewMeasureUnitNameAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final String sourceName = source.getName();
        final String measureUnitName = measureUnit.getName();
        assertEquals(sourceName, measureUnitName);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new MeasureUnit version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewMeasureUnitVersionAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final Long sourceVersion = source.getVersion();
        final Long measureUnitVersion = measureUnit.getVersion();
        assertEquals(sourceVersion, measureUnitVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source id, name and version to new MeasureUnit id, name and version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdNameVersionToNewMeasureUnitIdNameVersionAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean sourceEqualsMeasureUnit = comparator.compare(measureUnit, source);
        assertTrue(sourceEqualsMeasureUnit);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}