package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.RandomDifficultyTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.factory.DifficultyTestFactory;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RandomDifficultyTestFactoryDefaultTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RandomDifficultyTestFactory integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RandomDifficultyTestFactoryIntegrationTest {

    private final DifficultyTestFactory factory;

    @Test
    @DisplayName("createDifficulty method should return random Difficulty object")
    void createDifficulty_shouldReturnRandomDifficulty() {
        final Difficulty difficulty = factory.createDifficulty();
        assertNotNull(difficulty);
    }
}