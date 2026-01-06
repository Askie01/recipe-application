package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class RandomDifficultyDTOTestFactory implements DifficultyDTOTestFactory {

    private final Faker faker;

    public DifficultyDTO createDifficultyDTO() {
        final String difficultyDTOName = getRandomDifficultyDTOName();
        return DifficultyDTO.valueOf(difficultyDTOName);
    }

    private String getRandomDifficultyDTOName() {
        final List<String> difficultyDTONames = getDifficultyDTONames();
        final int numberOfDifficultyDTOs = difficultyDTONames.size();
        final int randomNumber = getRandomNumber(numberOfDifficultyDTOs - 1);
        return difficultyDTONames.get(randomNumber);
    }

    private List<String> getDifficultyDTONames() {
        return Arrays
                .stream(DifficultyDTO.values())
                .map(Enum::name)
                .toList();
    }

    private int getRandomNumber(int highestNumber) {
        return faker.number().numberBetween(0, highestNumber);
    }
}
