package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.builder.HasServingsTestBuilder;
import com.askie01.recipeapplication.comparator.ServingsTestComparator;
import com.askie01.recipeapplication.comparator.ServingsValueTestComparator;
import com.askie01.recipeapplication.model.value.HasServings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ServingsValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ServingsValueTestComparatorUnitTest {

    private HasServings source;
    private HasServings target;
    private ServingsTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new ServingsValueTestComparator();
        this.source = HasServingsTestBuilder.builder()
                .servings(1.0)
                .build();
        this.target = HasServingsTestBuilder.builder()
                .servings(2.0)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source servings is equal to target servings")
    void compare_whenSourceServingsIsEqualToTargetServings_returnsTrue() {
        final double sourceServings = source.getServings();
        target.setServings(sourceServings);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source servings is not equal to target servings")
    void compare_whenSourceServingsIsNotEqualToTargetServings_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source servings is null")
    void compare_whenSourceServingsIsNull_returnsFalse() {
        source.setServings(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target servings is null")
    void compare_whenTargetServingsIsNull_returnsFalse() {
        target.setServings(null);
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