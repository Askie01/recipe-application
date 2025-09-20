package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.comparator.*;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MeasureUnitMeasureUnitDTOValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class MeasureUnitMeasureUnitDTOValueTestComparatorUnitTest {

    @Mock
    private LongIdTestComparator longIdTestComparator;

    @Mock
    private StringNameTestComparator stringNameTestComparator;

    @Mock
    private LongVersionTestComparator longVersionTestComparator;
    private MeasureUnitMeasureUnitDTOTestComparator comparator;
    private MeasureUnit measureUnit;
    private MeasureUnitDTO measureUnitDTO;

    @BeforeEach
    void setUp() {
        this.comparator = new MeasureUnitMeasureUnitDTOValueTestComparator(
                longIdTestComparator,
                stringNameTestComparator,
                longVersionTestComparator);
        final Faker faker = new Faker();
        this.measureUnit = new RandomMeasureUnitTestFactory(faker).createMeasureUnit();
        this.measureUnitDTO = new RandomMeasureUnitDTOTestFactory(faker).createMeasureUnitDTO();
    }

    @Test
    @DisplayName("compare method should return true when measureUnit and measureUnitDTO have the same common field values")
    void compare_whenMeasureUnitHaveTheSameCommonFieldValuesAsMeasureUnitDTO_returnsTrue() {
        when(longIdTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(true);
        when(stringNameTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(true);
        when(longVersionTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(true);
        final boolean result = comparator.compare(measureUnit, measureUnitDTO);
        assertTrue(result);
    }

    @ParameterizedTest(name = "compare method should return false when MeasureUnit and MeasureUnitDTO have one common field with different value")
    @MethodSource("booleanTriples")
    void compare_whenMeasureUnitAndMeasureUnitDTOHaveOneCommonFieldWithDifferentValue_returnsFalse(boolean idEqual,
                                                                                                   boolean nameEqual,
                                                                                                   boolean versionEqual) {
        when(longIdTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(idEqual);
        when(stringNameTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(nameEqual);
        when(longVersionTestComparator
                .compare(any(MeasureUnit.class), any(MeasureUnitDTO.class)))
                .thenReturn(versionEqual);
        final boolean result = comparator.compare(measureUnit, measureUnitDTO);
        assertFalse(result);
    }

    private static Stream<Arguments> booleanTriples() {
        return Stream.of(
                Arguments.of(false, true, true),
                Arguments.of(true, false, true),
                Arguments.of(true, true, false)
        );
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnit is null")
    void compare_whenMeasureUnitIsNull_throwsNullPointerException() {
        when(longIdTestComparator
                .compare(eq(null), any(MeasureUnitDTO.class)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> comparator.compare(null, measureUnitDTO));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when measureUnitDTO is null")
    void compare_whenMeasureUnitDTOIsNull_throwsNullPointerException() {
        when(longIdTestComparator
                .compare(any(MeasureUnit.class), eq(null)))
                .thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> comparator.compare(measureUnit, null));
    }
}