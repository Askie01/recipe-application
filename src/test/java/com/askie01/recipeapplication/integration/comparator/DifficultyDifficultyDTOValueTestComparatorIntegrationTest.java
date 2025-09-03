package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.comparator.DifficultyDifficultyDTOTestComparator;
import com.askie01.recipeapplication.configuration.DifficultyDifficultyDTOValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;
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
        DifficultyDifficultyDTOValueTestComparatorTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("DifficultyDifficultyDTOValueTestComparator integration tests")
class DifficultyDifficultyDTOValueTestComparatorIntegrationTest {

    private final DifficultyDifficultyDTOTestComparator comparator;

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