package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.mapper.DifficultyToDifficultyDTOMapper;
import com.askie01.recipeapplication.mapper.SimpleDifficultyToDifficultyDTOMapper;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("SimpleDifficultyToDifficultyDTOMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class SimpleDifficultyToDifficultyDTOMapperUnitTest {

    private DifficultyToDifficultyDTOMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new SimpleDifficultyToDifficultyDTOMapper();
    }

    @Test
    @DisplayName("mapToValue method should return DifficultyDTO.EASY when source is Difficulty.EASY")
    void mapToValue_whenSourceIsDifficultyEasy_returnsDifficultyDTOEasy() {
        final DifficultyDTO expected = DifficultyDTO.EASY;
        final DifficultyDTO result = mapper.mapToValue(Difficulty.EASY);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("mapToValue method should return DifficultyDTO.MEDIUM when source is Difficulty.MEDIUM")
    void mapToValue_whenSourceIsDifficultyMedium_returnsDifficultyDTOMedium() {
        final DifficultyDTO expected = DifficultyDTO.MEDIUM;
        final DifficultyDTO result = mapper.mapToValue(Difficulty.MEDIUM);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("mapToValue method should return DifficultyDTO.HARD when source is Difficulty.HARD")
    void mapToValue_whenSourceIsDifficultyHard_returnsDifficultyDTOHard() {
        final DifficultyDTO expected = DifficultyDTO.HARD;
        final DifficultyDTO actual = mapper.mapToValue(Difficulty.HARD);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("mapToValue method should throw NullPointerException when source is null")
    void mapToValue_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToValue(null));
    }
}