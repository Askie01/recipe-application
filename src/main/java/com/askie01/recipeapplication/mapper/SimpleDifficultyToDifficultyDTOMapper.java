package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.model.entity.value.Difficulty;

public class SimpleDifficultyToDifficultyDTOMapper implements DifficultyToDifficultyDTOMapper {

    @Override
    public DifficultyDTO mapToValue(Difficulty difficulty) {
        return mapToDifficultyDTO(difficulty);
    }

    private DifficultyDTO mapToDifficultyDTO(Difficulty difficulty) {
        final String difficultyName = difficulty.name();
        return DifficultyDTO.valueOf(difficultyName);
    }
}
