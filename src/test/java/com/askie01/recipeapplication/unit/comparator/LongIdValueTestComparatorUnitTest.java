package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.comparator.LongIdTestComparator;
import com.askie01.recipeapplication.comparator.LongIdValueTestComparator;
import com.askie01.recipeapplication.model.value.HasLongId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LongIdValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class LongIdValueTestComparatorUnitTest {

    private HasLongId source;
    private HasLongId target;
    private LongIdTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new LongIdValueTestComparator();
        this.source = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
        this.target = HasLongIdTestBuilder.builder()
                .id(2L)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source id is equal to target id")
    void compare_whenSourceIdIsEqualToTargetId_returnsTrue() {
        final Long sourceId = source.getId();
        target.setId(sourceId);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source id is not equal to target id")
    void compare_whenSourceIdIsNotEqualToTargetId_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source id is null")
    void compare_whenSourceIdIsNull_returnsFalse() {
        source.setId(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target id is null")
    void compare_whenTargetIdIsNull_returnsFalse() {
        target.setId(null);
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