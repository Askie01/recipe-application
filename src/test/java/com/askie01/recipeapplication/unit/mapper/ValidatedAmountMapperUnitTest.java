package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.TestHasAmountBuilder;
import com.askie01.recipeapplication.mapper.AmountMapper;
import com.askie01.recipeapplication.mapper.ValidatedAmountMapper;
import com.askie01.recipeapplication.model.value.HasAmount;
import com.askie01.recipeapplication.validator.AmountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedAmountMapper unit tests")
class ValidatedAmountMapperUnitTest {

    @Mock
    private AmountValidator validator;
    private AmountMapper mapper;
    private HasAmount source;
    private HasAmount target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedAmountMapper(validator);
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
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final Double sourceAmount = source.getAmount();
        final Double targetAmount = target.getAmount();
        assertEquals(sourceAmount, targetAmount);
    }

    @Test
    @DisplayName("map method should not map source amount to target amount when source is invalid")
    void map_whenSourceAmountIsInvalid_doesNotMapSourceAmountToTargetAmount() {
        when(validator.isValid(source)).thenReturn(false);
        final Double targetAmountBeforeMapping = target.getAmount();
        mapper.map(source, target);
        final Double targetAmountAfterMapping = target.getAmount();
        assertEquals(targetAmountBeforeMapping, targetAmountAfterMapping);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source amount is null")
    void map_whenSourceAmountIsNull_throwsNullPointerException() {
        source.setAmount(null);
        when(validator.isValid(source)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        when(validator.isValid(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        when(validator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}