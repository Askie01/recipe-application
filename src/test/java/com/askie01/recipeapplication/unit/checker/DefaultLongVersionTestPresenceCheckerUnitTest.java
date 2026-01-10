package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.checker.DefaultLongVersionTestPresenceChecker;
import com.askie01.recipeapplication.checker.LongVersionTestPresenceChecker;
import com.askie01.recipeapplication.model.value.HasLongVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DefaultLongVersionTestPresenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultLongVersionTestPresenceCheckerUnitTest {

    private HasLongVersion source;
    private LongVersionTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultLongVersionTestPresenceChecker();
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
    }

    @Test
    @DisplayName("hasVersion method should return true when source version is positive")
    void hasVersion_whenSourceVersionIsPositive_returnsTrue() {
        final boolean result = checker.hasVersion(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasVersion method should return false when source version is negative")
    void hasVersion_whenSourceVersionIsNegative_returnsFalse() {
        source.setVersion(-1L);
        final boolean result = checker.hasVersion(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasVersion method should return true when source version is zero")
    void hasVersion_whenSourceVersionIsZero_returnsTrue() {
        source.setVersion(0L);
        final boolean result = checker.hasVersion(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasVersion method should return false when source version is null")
    void hasVersion_whenSourceVersionIsNull_returnsFalse() {
        source.setVersion(null);
        final boolean result = checker.hasVersion(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasVersion method should throw NullPointerException when source is null")
    void hasVersion_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasVersion(null));
    }
}