package com.askie01.recipeapplication.integration.checker;

import com.askie01.recipeapplication.checker.RecipeDifficultyTestPresenceChecker;
import com.askie01.recipeapplication.configuration.DefaultRecipeDifficultyTestPresenceCheckerTestConfiguration;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DefaultRecipeDifficultyTestPresenceCheckerTestConfiguration.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultRecipeDifficultyTestPresenceChecker integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultRecipeDifficultyTestPresenceCheckerIntegrationTest {

    private Recipe source;
    private final RecipeDifficultyTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.source = Recipe.builder()
                .difficulty(Difficulty.EASY)
                .build();
    }

    @Test
    @DisplayName("hasDifficulty method should return true when source difficulty is not null")
    void hasDifficulty_whenSourceDifficultyIsNotNull_returnsTrue() {
        final boolean result = checker.hasDifficulty(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasDifficulty method should return false when source difficulty is null")
    void hasDifficulty_whenSourceDifficultyIsNull_returnsFalse() {
        source.setDifficulty(null);
        final boolean result = checker.hasDifficulty(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasDifficulty method should throw NullPointerException when source is null")
    void hasDifficulty_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasDifficulty(null));
    }
}