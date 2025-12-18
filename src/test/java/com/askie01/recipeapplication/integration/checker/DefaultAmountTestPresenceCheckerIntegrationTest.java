package com.askie01.recipeapplication.integration.checker;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.checker.AmountTestPresenceChecker;
import com.askie01.recipeapplication.configuration.DefaultAmountTestPresenceCheckerTestConfiguration;
import com.askie01.recipeapplication.model.value.HasAmount;
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
@ContextConfiguration(classes = DefaultAmountTestPresenceCheckerTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("DefaultAmountTestPresenceChecker integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class DefaultAmountTestPresenceCheckerIntegrationTest {

    private HasAmount source;
    private final AmountTestPresenceChecker checker;

    @BeforeEach
    void setUp() {
        this.source = HasAmountTestBuilder.builder()
                .amount(1.0)
                .build();
    }

    @Test
    @DisplayName("hasAmount method should return true when source amount is positive")
    void hasAmount_whenSourceAmountIsPositive_returnsTrue() {
        final boolean result = checker.hasAmount(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("hasAmount method should return false when source amount is zero")
    void hasAmount_whenSourceAmountIsZero_returnsFalse() {
        source.setAmount(0.0);
        final boolean result = checker.hasAmount(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasAmount method should return false when source amount is negative")
    void hasAmount_whenSourceAmountIsNegative_returnsFalse() {
        source.setAmount(-1.0d);
        final boolean result = checker.hasAmount(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasAmount method should return false when source amount is null")
    void hasAmount_whenSourceAmountIsNull_returnsFalse() {
        source.setAmount(null);
        final boolean result = checker.hasAmount(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("hasAmount method should throw NullPointerException when source is null")
    void hasAmount_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> checker.hasAmount(null));
    }
}