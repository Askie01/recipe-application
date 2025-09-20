package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOTestFactory;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomMeasureUnitDTOTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomMeasureUnitDTOTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;
    private MeasureUnitDTOTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomMeasureUnitDTOTestFactory(faker);
    }

    @Test
    @DisplayName("createMeasureUnitDTO method should return random MeasureUnitDTO object")
    void createMeasureUnitDTO_shouldReturnRandomMeasureUnitDTO() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), eq(false))).thenReturn(0L);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple measureUnitDTO name");

        final MeasureUnitDTO measureUnitDTO = factory.createMeasureUnitDTO();
        final Long measureUnitId = measureUnitDTO.getId();
        final String measureUnitName = measureUnitDTO.getName();
        final Long measureUnitVersion = measureUnitDTO.getVersion();

        assertNotNull(measureUnitDTO);
        assertNotNull(measureUnitId);
        assertNotNull(measureUnitName);
        assertNotNull(measureUnitVersion);
    }
}