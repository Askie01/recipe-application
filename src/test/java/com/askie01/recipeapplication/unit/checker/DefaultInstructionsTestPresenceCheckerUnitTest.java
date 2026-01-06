package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.checker.DefaultInstructionsTestPresenceChecker;
import com.askie01.recipeapplication.checker.InstructionsTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasInstructions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultInstructionsTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultInstructionsTestPresenceCheckerUnitTest {

    private HasInstructions source;
    private InstructionsTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultInstructionsTestPresenceChecker();
        this.source = HasInstructionsTestBuilder.builder()
                .instructions("source instructions")
                .build();
    }

    @Test
    @DisplayName("hasInstructions method should return true when source instructions are not null")
    void hasInstructions_whenSourceInstructionsAreNotNull_returnsTrue() {
        final boolean result = checker.hasInstructions(source);
        assertTrue(result);
    }

    @ParameterizedTest(name = "hasInstructions method should return false when source instructions are blank")
    @ValueSource(strings = {" ", ""})
    void hasInstructions_whenSourceInstructionsAreBlank_returnsFalse(String instructions) {
        source.setInstructions(instructions);
        final boolean result = checker.hasInstructions(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasInstructions method should return false when source instructions are null")
    void hasInstructions_whenSourceInstructionsAreNull_returnsFalse() {
        source.setInstructions(null);
        final boolean result = checker.hasInstructions(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasInstructions method should throw NullPointerException when source is null")
    void hasInstructions_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasInstructions(null));
    }
}