package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.mapper.DifficultyDTOToDifficultyMapper;
import com.askie01.recipeapplication.mapper.SimpleDifficultyDTOToDifficultyMapper;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("SimpleDifficultyDTOToDifficultyMapper unit tests")
class SimpleDifficultyDTOToDifficultyMapperUnitTest {

    private DifficultyDTOToDifficultyMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleDifficultyDTOToDifficultyMapper();
    }

    @Test
    @DisplayName("mapToValue method should return DifficultyEasy when source is DifficultyDTOEasy")
    void mapToValue_whenSourceIsDifficultyDTOEasy_returnsDifficultyEasy() {
        final Difficulty actualResult = mapper.mapToValue(DifficultyDTO.EASY);
        final Difficulty expectedResult = Difficulty.EASY;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("mapToValue method should return DifficultyMedium when source is DifficultyDTOMedium")
    void mapToValue_whenSourceIsDifficultyDTOMedium_returnsDifficultyMedium() {
        final Difficulty actualResult = mapper.mapToValue(DifficultyDTO.MEDIUM);
        final Difficulty expectedResult = Difficulty.MEDIUM;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("mapToValue method should return DifficultyHard when source is DifficultyDTOHard")
    void mapToValue_whenSourceIsDifficultyDTOHard_returnsDifficultyHard() {
        final Difficulty actualResult = mapper.mapToValue(DifficultyDTO.HARD);
        final Difficulty expectedResult = Difficulty.HARD;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("mapToValue method should throw NullPointerException when source is null")
    void mapToValue_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToValue(null));
    }
}