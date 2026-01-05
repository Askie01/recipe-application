package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.comparator.AmountTestComparator;
import com.askie01.recipeapplication.comparator.AmountValueTestComparator;
import com.askie01.recipeapplication.model.value.HasAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AmountValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class AmountValueTestComparatorUnitTest {

    private HasAmount source;
    private HasAmount target;
    private AmountTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new AmountValueTestComparator();
        this.source = HasAmountTestBuilder.builder()
                .amount(1.0)
                .build();
        this.target = HasAmountTestBuilder.builder()
                .amount(2.0)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source amount is equal to target amount")
    void compare_whenSourceAmountIsEqualToTargetAmount_returnsTrue() {
        final double sourceAmount = source.getAmount();
        target.setAmount(sourceAmount);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source amount is not equal to target amount")
    void compare_whenSourceAmountIsNotEqualToTargetAmount_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source amount is null")
    void compare_whenSourceAmountIsNull_returnsFalse() {
        source.setAmount(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target amount is null")
    void compare_whenTargetAmountIsNull_returnsFalse() {
        target.setAmount(null);
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