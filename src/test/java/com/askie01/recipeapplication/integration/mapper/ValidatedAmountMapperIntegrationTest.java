package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.TestHasAmountBuilder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ValidatedAmountMapperConfiguration.class,
        PositiveAmountValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.amount-type=validated-amount",
        "component.validator.amount-type=positive-amount"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedAmountMapper integration tests")
class ValidatedAmountMapperIntegrationTest {

    private final AmountMapper mapper;
    private HasAmount source;
    private HasAmount target;

    @BeforeEach
    void setUp() {
        this.source = TestHasAmountBuilder.builder()
                .amount(1.0)
                .build();
        this.target = TestHasAmountBuilder.builder()
                .amount(2.0)
                .build();
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is valid")
    void map_whenSourceAmountIsValid_mapsSourceAmountToTargetAmount() {
        mapper.map(source, target);
        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        assertEquals(sourceAmount, targetAmount);
    }

    @Test
    @DisplayName("map method should not map source amount to target amount when source is invalid")
    void map_whenSourceAmountIsInvalid_doesNotMapSourceAmountToTargetAmount() {
        source.setAmount(-1.0);
        final Double targetAmountBeforeMapping = target.getAmount();
        mapper.map(source, target);
        final Double targetAmountAfterMapping = target.getAmount();
        assertEquals(targetAmountBeforeMapping, targetAmountAfterMapping);
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