package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.checker.DefaultStringNameTestPresenceChecker;
import com.askie01.recipeapplication.checker.StringNameTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasStringName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultStringNameTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultStringNameTestPresenceCheckerUnitTest {

    private HasStringName source;
    private StringNameTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultStringNameTestPresenceChecker();
        this.source = HasStringNameTestBuilder.builder()
                .name("source name")
                .build();
    }

    @Test
    @DisplayName("hasName method should return true when source name is not null")
    void hasName_whenSourceNameIsNotNull_returnsTrue() {
        final boolean result = checker.hasName(source);
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    @DisplayName("hasName method should return false when source name is blank")
    void hasName_whenSourceNameIsBlank_returnsFalse(String name) {
        source.setName(name);
        final boolean result = checker.hasName(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasName method should return false when source name is null")
    void hasName_whenSourceNameIsNull_returnsFalse() {
        source.setName(null);
        final boolean result = checker.hasName(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasName method should throw NullPointerException when source is null")
    void hasName_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasName(null));
    }
}