package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.configuration.AmountValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasAmount;
import com.askie01.recipeapplication.validator.AmountValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = AmountValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.amount-type=positive-amount")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("PositiveAmountValidator integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class PositiveAmountValidatorIntegrationTest {

    private final AmountValidator validator;

    @Test
    @DisplayName("isValid method should return true when argument's amount is positive")
    void isValid_whenArgumentAmountIsPositive_returnsTrue() {
        final HasAmount argument = HasAmountTestBuilder.builder()
                .amount(5d)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when argument's amount is negative")
    void isValid_whenArgumentAmountIsNegative_returnsFalse() {
        final HasAmount argument = HasAmountTestBuilder.builder()
                .amount(-5d)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument's amount is null")
    void isValid_whenArgumentAmountIsNull_throwsNullPointerException() {
        final HasAmount argument = HasAmountTestBuilder.builder()
                .amount(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if argument is null")
    void isValid_whenArgumentIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}