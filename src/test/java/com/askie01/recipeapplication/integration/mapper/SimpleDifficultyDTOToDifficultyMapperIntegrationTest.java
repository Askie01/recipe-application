package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.configuration.SimpleDifficultyDTOToDifficultyMapperConfiguration;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.mapper.DifficultyDTOToDifficultyMapper;
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
@ContextConfiguration(classes = SimpleDifficultyDTOToDifficultyMapperConfiguration.class)
@TestPropertySource(properties = "component.mapper.difficultyDTO-to-difficulty-type=simple")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("SimpleDifficultyDTOToDifficultyMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class SimpleDifficultyDTOToDifficultyMapperIntegrationTest {

    private final DifficultyDTOToDifficultyMapper mapper;

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