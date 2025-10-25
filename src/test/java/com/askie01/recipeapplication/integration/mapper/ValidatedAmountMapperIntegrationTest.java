package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.comparator.AmountTestComparator;
import com.askie01.recipeapplication.configuration.AmountValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.PositiveAmountValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedAmountMapperConfiguration;
import com.askie01.recipeapplication.mapper.AmountMapper;
import com.askie01.recipeapplication.model.value.HasAmount;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
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
@ContextConfiguration(classes = {
        ValidatedAmountMapperConfiguration.class,
        PositiveAmountValidatorConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class
})
@TestPropertySource(properties = {
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedAmountMapper integration tests")
class ValidatedAmountMapperIntegrationTest {

    private final AmountMapper mapper;
    private HasAmount source;
    private HasAmount target;

    private final AmountTestComparator amountComparator;

    @BeforeEach
    void setUp() {
        this.source = HasAmountTestBuilder.builder()
                .amount(1.0)
                .build();
        this.target = HasAmountTestBuilder.builder()
                .amount(2.0)
                .build();
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is valid")
    void map_whenSourceAmountIsValid_mapsSourceAmountToTargetAmount() {
        mapper.map(source, target);
        final boolean equalAmount = amountComparator.compare(source, target);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("map method should not map source amount to target amount when source is invalid")
    void map_whenSourceAmountIsInvalid_doesNotMapSourceAmountToTargetAmount() {
        mapper.map(source, target);
        source.setAmount(-1.0);
        final boolean equalAmount = amountComparator.compare(source, target);
        assertFalse(equalAmount);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source amount is null")
    void map_whenSourceAmountIsNull_throwsNullPointerException() {
        source.setAmount(null);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}