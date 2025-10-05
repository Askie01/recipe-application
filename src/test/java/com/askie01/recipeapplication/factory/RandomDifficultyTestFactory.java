package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class RandomDifficultyTestFactory implements DifficultyTestFactory {

    private final Faker faker;

    public Difficulty createDifficulty() {
        final String difficultyName = getRandomDifficultyName();
        return Difficulty.valueOf(difficultyName);
    }

    private String getRandomDifficultyName() {
        final List<String> difficultyNames = getDifficultyNames();
        final int numberOfDifficulties = difficultyNames.size();
        final int randomNumber = getRandomNumber(numberOfDifficulties - 1);
        return difficultyNames.get(randomNumber);
    }

    private List<String> getDifficultyNames() {
        return Arrays
                .stream(Difficulty.values())
                .map(Enum::name)
                .toList();
    }

    private int getRandomNumber(int highestNumber) {
        return faker.number().numberBetween(0, highestNumber);
    }
}
