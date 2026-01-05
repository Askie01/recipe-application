package com.askie01.recipeapplication.integration.checker;

import com.askie01.recipeapplication.builder.HasLongIdTestBuilder;
import com.askie01.recipeapplication.checker.LongIdTestPresenceChecker;
import com.askie01.recipeapplication.configuration.DefaultLongIdTestPresenceCheckerTestConfiguration;
import com.askie01.recipeapplication.model.value.HasLongId;
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
@ContextConfiguration(classes = DefaultLongIdTestPresenceCheckerTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultLongIdTestPresenceChecker integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultLongIdTestPresenceCheckerIntegrationTest {

    private HasLongId source;
    private final LongIdTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.source = HasLongIdTestBuilder.builder()
                .id(1L)
                .build();
    }

    @Test
    @DisplayName("hasId method should return true when source id is positive")
    void hasId_whenSourceIdIsPositive_returnsTrue() {
        final boolean result = checker.hasId(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasId method should return false when source id is negative")
    void hasId_whenSourceIdIsNegative_returnsFalse() {
        source.setId(-1L);
        final boolean result = checker.hasId(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasId method should return false when source id is zero")
    void hasId_whenSourceIdIsZero_returnsFalse() {
        source.setId(0L);
        final boolean result = checker.hasId(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasId method should return false when source id is null")
    void hasId_whenSourceIdIsNull_returnsFalse() {
        source.setId(null);
        final boolean result = checker.hasId(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasId method should throw NullPointerException when source is null")
    void hasId_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasId(null));
    }
}