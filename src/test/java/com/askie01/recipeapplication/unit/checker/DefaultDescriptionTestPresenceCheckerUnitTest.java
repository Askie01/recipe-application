package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasDescriptionTestBuilder;
import com.askie01.recipeapplication.checker.DefaultDescriptionTestPresenceChecker;
import com.askie01.recipeapplication.checker.DescriptionTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasDescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultDescriptionTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultDescriptionTestPresenceCheckerUnitTest {

    private HasDescription source;
    private DescriptionTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultDescriptionTestPresenceChecker();
        this.source = HasDescriptionTestBuilder.builder()
                .description("source description")
                .build();
    }

    @Test
    @DisplayName("hasDescription method should return true when source description is not null")
    void hasDescription_whenSourceDescriptionIsNotNull_returnsTrue() {
        final boolean result = checker.hasDescription(source);
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    @DisplayName("hasDescription method should return false when source description is blank")
    void hasDescription_whenSourceDescriptionIsBlank_returnsFalse(String description) {
        source.setDescription(description);
        final boolean result = checker.hasDescription(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasDescription method should return false when source description is null")
    void hasDescription_whenSourceDescriptionIsNull_returnsFalse() {
        source.setDescription(null);
        final boolean result = checker.hasDescription(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasDescription method should throw NullPointerException when source is null")
    void hasDescription_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasDescription(null));
    }
}