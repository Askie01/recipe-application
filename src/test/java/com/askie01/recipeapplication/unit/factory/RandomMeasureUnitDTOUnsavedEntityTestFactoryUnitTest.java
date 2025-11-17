package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.MeasureUnitDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitDTOUnsavedEntityTestFactory;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomMeasureUnitDTOUnsavedEntityTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomMeasureUnitDTOUnsavedEntityTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private FunnyName fakerFunnyName;
    private MeasureUnitDTOUnsavedEntityTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomMeasureUnitDTOUnsavedEntityTestFactory(faker);
    }

    @Test
    @DisplayName("createMeasureUnitDTO method should return random MeasureUnitDTO entity-like object")
    void createMeasureUnitDTO_shouldReturnRandomMeasureUnitDTOUnsavedEntity() {
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple measureUnitDTO name");

        final MeasureUnitDTO measureUnitDTO = factory.createMeasureUnitDTO();
        final Long measureUnitId = measureUnitDTO.getId();
        final String measureUnitName = measureUnitDTO.getName();
        final Long measureUnitVersion = measureUnitDTO.getVersion();

        assertNotNull(measureUnitDTO);
        assertNull(measureUnitId);
        assertNotNull(measureUnitName);
        assertNull(measureUnitVersion);
    }
}