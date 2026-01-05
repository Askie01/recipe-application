package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.comparator.AmountTestComparator;
import com.askie01.recipeapplication.comparator.AmountValueTestComparator;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ValidatedAmountMapper unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class ValidatedAmountMapperUnitTest {

    private HasAmount source;
    private HasAmount target;
    private AmountMapper mapper;

    @Mock
    private AmountValidator validator;
    private AmountTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedAmountMapper(validator);
        this.source = HasAmountTestBuilder.builder()
                .amount(1.0)
                .build();
        this.target = HasAmountTestBuilder.builder()
                .amount(2.0)
                .build();
        this.comparator = new AmountValueTestComparator();
    }

    @Test
    @DisplayName("map method should map source amount to target amount when source is valid")
    void map_whenSourceAmountIsValid_mapsSourceAmountToTargetAmount() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final boolean equalAmount = comparator.compare(source, target);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("map method should not map source amount to target amount when source is invalid")
    void map_whenSourceAmountIsInvalid_doesNotMapSourceAmountToTargetAmount() {
        when(validator.isValid(source)).thenReturn(false);
        mapper.map(source, target);
        final boolean equalAmount = comparator.compare(source, target);
        assertFalse(equalAmount);
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