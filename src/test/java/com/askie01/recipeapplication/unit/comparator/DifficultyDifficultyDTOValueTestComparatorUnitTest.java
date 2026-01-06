package com.askie01.recipeapplication.unit.comparator;

import com.askie01.recipeapplication.comparator.DifficultyDifficultyDTOValueTestComparator;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DifficultyDifficultyDTOValueTestComparator unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DifficultyDifficultyDTOValueTestComparatorUnitTest {

    private DifficultyDifficultyDTOValueTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.comparator = new DifficultyDifficultyDTOValueTestComparator();
    }

    @Test
    @DisplayName("compare method should return true when difficulty and difficultyDTO have the same constant")
    void compare_whenDifficultyAndDifficultyDTOHaveTheSameConstant_returnsTrue() {
        final boolean result = comparator.compare(Difficulty.EASY, DifficultyDTO.EASY);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when difficulty and difficultyDTO have different constants")
    void compare_whenDifficultyAndDifficultyDTOHaveDifferentConstants_returnsFalse() {
        final boolean result = comparator.compare(Difficulty.EASY, DifficultyDTO.MEDIUM);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when difficulty is null")
    void compare_whenDifficultyIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, DifficultyDTO.EASY));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when difficultyDTO is null")
    void compare_whenDifficultyDTOIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(Difficulty.EASY, null));
    }
}