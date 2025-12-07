package com.askie01.recipeapplication.integration.checker;

import com.askie01.recipeapplication.builder.HasServingsTestBuilder;
import com.askie01.recipeapplication.checker.ServingsTestPresenceChecker;
import com.askie01.recipeapplication.configuration.DefaultServingsTestPresenceCheckerTestConfiguration;
import com.askie01.recipeapplication.model.value.HasServings;
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
@ContextConfiguration(classes = {
        DefaultServingsTestPresenceCheckerTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultServingsTestPresenceChecker integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultServingsTestPresenceCheckerIntegrationTest {

    private HasServings source;
    private final ServingsTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.source = HasServingsTestBuilder.builder()
                .servings(1.0)
                .build();
    }

    @Test
    @DisplayName("hasServings method should return true when source servings are positive")
    void hasServings_whenSourceServingsArePositive_returnsTrue() {
        final boolean result = checker.hasServings(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasServings method should return false when source servings are negative")
    void hasServings_whenSourceServingsAreNegative_returnsFalse() {
        source.setServings(-1.0);
        final boolean result = checker.hasServings(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasServings method should return false when source servings are zero")
    void hasServings_whenSourceServingsAreZero_returnsFalse() {
        source.setServings(0.0);
        final boolean result = checker.hasServings(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasServings method should return false when source servings are null")
    void hasServings_whenSourceServingsAreNull_returnsFalse() {
        source.setServings(null);
        final boolean result = checker.hasServings(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasServings method should throw NullPointerException when source is null")
    void hasServings_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasServings(null));
    }
}