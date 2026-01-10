package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.SimpleDifficultyToDifficultyDTOMapperConfiguration;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.mapper.DifficultyToDifficultyDTOMapper;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SimpleDifficultyToDifficultyDTOMapperConfiguration.class)
@TestPropertySource(properties = "component.mapper.difficulty-to-difficultyDTO-type=simple")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleDifficultyToDifficultyDTOMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleDifficultyToDifficultyDTOMapperIntegrationTest {

    private final DifficultyToDifficultyDTOMapper mapper;

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
        final DifficultyDTO result = mapper.mapToValue(Difficulty.HARD);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("mapToValue method should throw NullPointerException when source is null")
    void mapToValue_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.mapToValue(null));
    }
}