package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.comparator.MeasureUnitMeasureUnitDTOTestComparator;
import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MeasureUnitMeasureUnitDTOValueTestComparatorDefaultTestConfiguration.class,
        LongIdValueTestComparatorTestConfiguration.class,
        StringNameValueTestComparatorTestConfiguration.class,
        LongVersionValueTestComparatorTestConfiguration.class,
        FakerTestConfiguration.class,
        RandomMeasureUnitTestFactoryDefaultTestConfiguration.class,
        RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("MeasureUnitMeasureUnitDTOValueTestComparator integration tests")
class MeasureUnitMeasureUnitDTOValueTestComparatorIntegrationTest {

    private final MeasureUnitMeasureUnitDTOTestComparator comparator;
    private final MeasureUnitTestFactory measureUnitFactory;
    private final MeasureUnitDTOTestFactory measureUnitDTOFactory;
    private MeasureUnit measureUnit;
    private MeasureUnitDTO measureUnitDTO;

    @BeforeEach
    void setUp() {
        this.measureUnit = measureUnitFactory.createMeasureUnit();
        this.measureUnitDTO = measureUnitDTOFactory.createMeasureUnitDTO();
    }

    @Test
    @DisplayName("compare method should return true when measureUnit and measureUnitDTO have the same common field values")
    void compare_whenMeasureUnitHaveTheSameCommonFieldValuesAsMeasureUnitDTO_returnsTrue() {
        final Long measureUnitId = measureUnit.getId();
        final String measureUnitName = measureUnit.getName();
        final Long measureUnitVersion = measureUnit.getVersion();
        measureUnitDTO.setId(measureUnitId);
        measureUnitDTO.setName(measureUnitName);
        measureUnitDTO.setVersion(measureUnitVersion);
        final boolean result = comparator.compare(measureUnit, measureUnitDTO);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when MeasureUnit and MeasureUnitDTO have different common field values")
    void compare_whenMeasureUnitAndMeasureUnitDTOHaveOneCommonFieldWithDifferentValue_returnsFalse() {
        final boolean result = comparator.compare(measureUnit, measureUnitDTO);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnit is null")
    void compare_whenMeasureUnitIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, measureUnitDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnitDTO is null")
    void compare_whenMeasureUnitDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(measureUnit, null));
    }
}