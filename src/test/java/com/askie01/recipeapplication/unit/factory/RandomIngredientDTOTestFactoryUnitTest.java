package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientDTOTestFactory;
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
@DisplayName("RandomIngredientDTOTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomIngredientDTOTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;

    @Mock
    private MeasureUnitDTOTestFactory measureUnitDTOTestFactory;
    private IngredientDTOTestFactory ingredientDTOTestFactory;

    @BeforeEach
    void setUp() {
        this.ingredientDTOTestFactory = new RandomIngredientDTOTestFactory(faker, measureUnitDTOTestFactory);
    }

    @Test
    @DisplayName("createIngredientDTO method should return random IngredientDTO object")
    void createIngredientDTO_shouldReturnRandomIngredientDTO() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), eq(false))).thenReturn(0L);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple ingredient name");
        when(measureUnitDTOTestFactory.createMeasureUnitDTO()).thenReturn(new MeasureUnitDTO());

        final IngredientDTO ingredientDTO = ingredientDTOTestFactory.createIngredientDTO();
        final Long ingredientId = ingredientDTO.getId();
        final String ingredientName = ingredientDTO.getName();
        final Double ingredientAmount = ingredientDTO.getAmount();
        final MeasureUnitDTO ingredientMeasureUnitDTO = ingredientDTO.getMeasureUnitDTO();
        final Long ingredientVersion = ingredientDTO.getVersion();

        assertNotNull(ingredientDTO);
        assertNotNull(ingredientId);
        assertNotNull(ingredientName);
        assertNotNull(ingredientAmount);
        assertNotNull(ingredientMeasureUnitDTO);
        assertNotNull(ingredientVersion);
    }
}