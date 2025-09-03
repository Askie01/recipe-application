package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DifficultyDifficultyDTOValueTestComparator implements DifficultyDifficultyDTOTestComparator {

    @Override
    public boolean compare(Difficulty difficulty, DifficultyDTO difficultyDTO) {
        return haveEqualName(difficulty, difficultyDTO);
    }

    private boolean haveEqualName(Difficulty difficulty, DifficultyDTO difficultyDTO) {
        final String difficultyName = difficulty.name();
        final String difficultyDTOName = difficultyDTO.name();
        return difficultyName.equals(difficultyDTOName);
    }
}
