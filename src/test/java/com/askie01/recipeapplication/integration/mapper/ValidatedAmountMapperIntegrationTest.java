package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasAmountTestBuilder;
import com.askie01.recipeapplication.comparator.AmountTestComparator;
import com.askie01.recipeapplication.configuration.AmountValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedAmountMapperDefaultTestConfiguration;
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
        ValidatedAmountMapperDefaultTestConfiguration.class,
        AmountValueTestComparatorTestConfiguration.class
})
@TestPropertySource(locations = "classpath:validated-amount-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedAmountMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedAmountMapperIntegrationTest {

    private HasAmount source;
    private HasAmount target;
    private final AmountMapper mapper;
    private final AmountTestComparator comparator;

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
        final boolean equalAmount = comparator.compare(source, target);
        assertTrue(equalAmount);
    }

    @Test
    @DisplayName("map method should not map source amount to target amount when source is invalid")
    void map_whenSourceAmountIsInvalid_doesNotMapSourceAmountToTargetAmount() {
        mapper.map(source, target);
        source.setAmount(-1.0);
        final boolean equalAmount = comparator.compare(source, target);
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