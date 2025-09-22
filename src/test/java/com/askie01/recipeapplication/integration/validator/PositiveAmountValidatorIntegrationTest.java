package com.askie01.recipeapplication.integration.validator;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.configuration.PositiveAmountValidatorConfiguration;
import com.askie01.recipeapplication.model.value.HasAmount;
import com.askie01.recipeapplication.validator.AmountValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PositiveAmountValidatorConfiguration.class)
@TestPropertySource(properties = "component.validator.amount-type=positive-amount")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("PositiveAmountValidator integration tests")
class PositiveAmountValidatorIntegrationTest {

    private final AmountValidator validator;

    @Test
    @DisplayName("isValid method should return true when source amount is positive")
    void isValid_whenSourceAmountIsPositive_returnsTrue() {
        final HasAmount argument = HasAmountTestBuilder.builder()
                .amount(5d)
                .build();
        final boolean result = validator.isValid(argument);
        assertTrue(result);
    }

    @Test
    @DisplayName("isValid method should return false when source amount is negative")
    void isValid_whenSourceAmountIsNegative_returnsFalse() {
        final HasAmount argument = HasAmountTestBuilder.builder()
                .amount(-5d)
                .build();
        final boolean result = validator.isValid(argument);
        assertFalse(result);
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source amount is null")
    void isValid_whenSourceAmountIsNull_throwsNullPointerException() {
        final HasAmount argument = HasAmountTestBuilder.builder()
                .amount(null)
                .build();
        assertThrows(NullPointerException.class, () -> validator.isValid(argument));
    }

    @Test
    @DisplayName("isValid method should throw NullPointerException if source is null")
    void isValid_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validator.isValid(null));
    }
}