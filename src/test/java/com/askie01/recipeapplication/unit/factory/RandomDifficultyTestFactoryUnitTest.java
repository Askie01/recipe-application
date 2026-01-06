package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.factory.DifficultyTestFactory;
import com.askie01.recipeapplication.factory.RandomDifficultyTestFactory;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomDifficultyTestFactory unit test")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomDifficultyTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;
    private DifficultyTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomDifficultyTestFactory(faker);
    }

    @Test
    @DisplayName("createDifficulty method should return random Difficulty object")
    void createDifficulty_shouldReturnRandomDifficulty() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.numberBetween(anyInt(), anyInt())).thenReturn(0);
        final Difficulty difficulty = factory.createDifficulty();
        assertNotNull(difficulty);
    }
}