package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.model.entity.value.Difficulty;

public class SimpleDifficultyDTOToDifficultyMapper implements DifficultyDTOToDifficultyMapper {

    @Override
    public Difficulty mapToValue(DifficultyDTO difficultyDTO) {
        return mapToDifficulty(difficultyDTO);
    }

    private Difficulty mapToDifficulty(DifficultyDTO difficultyDTO) {
        final String difficultyDTOName = difficultyDTO.name();
        return Difficulty.valueOf(difficultyDTOName);
    }
}
