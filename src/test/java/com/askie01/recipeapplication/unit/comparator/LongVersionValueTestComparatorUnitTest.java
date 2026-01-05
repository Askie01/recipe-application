package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.comparator.LongVersionTestComparator;
import com.askie01.recipeapplication.comparator.LongVersionValueTestComparator;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LongVersionValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class LongVersionValueTestComparatorUnitTest {

    private HasLongVersion source;
    private HasLongVersion target;
    private LongVersionTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new LongVersionValueTestComparator();
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
        this.target = HasLongVersionTestBuilder.builder()
                .version(2L)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source version is equal to target version")
    void compare_whenSourceVersionIsEqualToTargetVersion_returnsTrue() {
        final Long sourceVersion = source.getVersion();
        target.setVersion(sourceVersion);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source version is not equal to target version")
    void compare_whenSourceVersionIsNotEqualToTargetVersion_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source version is null")
    void compare_whenSourceVersionIsNull_returnsFalse() {
        source.setVersion(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target version is null")
    void compare_whenTargetVersionIsNull_returnsFalse() {
        target.setVersion(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when source is null")
    void compare_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, target));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when target is null")
    void compare_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(source, null));
    }
}