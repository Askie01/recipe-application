package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.comparator.InstructionsTestComparator;
import com.askie01.recipeapplication.comparator.InstructionsValueTestComparator;
import com.askie01.recipeapplication.model.value.HasInstructions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InstructionValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class InstructionsValueTestComparatorUnitTest {

    private HasInstructions source;
    private HasInstructions target;
    private InstructionsTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new InstructionsValueTestComparator();
        this.source = HasInstructionsTestBuilder.builder()
                .instructions("source instructions")
                .build();
        this.target = HasInstructionsTestBuilder.builder()
                .instructions("target instructions")
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source instructions are equal to target instructions")
    void compare_whenSourceInstructionsAreEqualToTargetInstructions_returnsTrue() {
        final String sourceInstructions = source.getInstructions();
        target.setInstructions(sourceInstructions);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source instructions are not equal to target instructions")
    void compare_whenSourceInstructionsAreNotEqualToTargetInstructions_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source instructions are null")
    void compare_whenSourceInstructionsAreNull_returnsFalse() {
        source.setInstructions(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target instructions are null")
    void compare_whenTargetInstructionsAreNull_returnsFalse() {
        target.setInstructions(null);
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