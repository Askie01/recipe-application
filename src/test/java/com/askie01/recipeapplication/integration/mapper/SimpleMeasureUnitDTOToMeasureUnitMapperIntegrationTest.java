package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.comparator.MeasureUnitMeasureUnitDTOTestComparator;
import com.askie01.recipeapplication.comparator.StringNameTestComparator;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleMeasureUnitDTOToMeasureUnitMapperDefaultTestConfiguration.class,
        RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration.class,
        RandomMeasureUnitTestFactoryDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        MeasureUnitMeasureUnitDTOValueTestComparatorDefaultTestConfiguration.class
})
@TestPropertySource(locations = "classpath:simple-measureUnitDTO-to-measureUnit-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleMeasureUnitDTOToMeasureUnitMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleMeasureUnitDTOToMeasureUnitMapperIntegrationTest {

    private MeasureUnitDTO source;
    private MeasureUnit target;
    private final LongIdTestComparator idComparator;
    private final StringNameTestComparator nameComparator;
    private final LongVersionTestComparator versionComparator;
    private final MeasureUnitMeasureUnitDTOTestComparator measureUnitComparator;
    private final MeasureUnitTestFactory measureUnitFactory;
    private final MeasureUnitDTOTestFactory measureUnitDTOFactory;
    private final MeasureUnitDTOToMeasureUnitMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = measureUnitDTOFactory.createMeasureUnitDTO();
        this.target = measureUnitFactory.createMeasureUnit();
    }

    @Test
    @DisplayName("map method should map source id to target id when source is present")
    void map_whenSourceIsPresent_mapsSourceIdToTargetId() {
        mapper.map(source, target);
        final boolean equalId = idComparator.compare(source, target);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("map method should map source name to target name when source is present")
    void map_whenSourceIsPresent_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final boolean equalName = nameComparator.compare(source, target);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("map method should map source version to target version when source is present")
    void map_whenSourceIsPresent_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final boolean equalVersion = versionComparator.compare(source, target);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("map method should map source id, name and version to target id, name and version when source is present")
    void map_whenSourceIsPresent_mapsSourceIdNameVersionToTargetIdNameVersion() {
        mapper.map(source, target);
        final boolean equalMeasureUnits = measureUnitComparator.compare(target, source);
        assertTrue(equalMeasureUnits);
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
        final boolean equalId = idComparator.compare(source, measureUnit);
        assertTrue(equalId);
    }

    @Test
    @DisplayName("mapToEntity method should map source name to new MeasureUnit name and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceNameToNewMeasureUnitNameAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalName = nameComparator.compare(source, measureUnit);
        assertTrue(equalName);
    }

    @Test
    @DisplayName("mapToEntity method should map source version to new MeasureUnit version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceVersionToNewMeasureUnitVersionAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalVersion = versionComparator.compare(source, measureUnit);
        assertTrue(equalVersion);
    }

    @Test
    @DisplayName("mapToEntity method should map source id, name and version to new MeasureUnit id, name and version and return it")
    void mapToEntity_whenSourceIsPresent_mapsSourceIdNameVersionToNewMeasureUnitIdNameVersionAndReturnIt() {
        final MeasureUnit measureUnit = mapper.mapToEntity(source);
        final boolean equalMeasureUnits = measureUnitComparator.compare(measureUnit, source);
        assertTrue(equalMeasureUnits);
    }

    @Test
    @DisplayName("mapToEntity method should throw NullPointerException when source is null")
    void mapToEntity_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToEntity(null));
    }
}