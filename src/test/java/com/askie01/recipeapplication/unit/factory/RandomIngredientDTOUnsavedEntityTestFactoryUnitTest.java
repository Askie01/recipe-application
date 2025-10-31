package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitDTOUnsavedEntityTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientDTOUnsavedEntityTestFactory;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomIngredientDTOUnsavedEntityTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomIngredientDTOUnsavedEntityTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;

    @Mock
    private MeasureUnitDTOUnsavedEntityTestFactory measureUnitFactory;
    private IngredientDTOUnsavedEntityTestFactory ingredientFactory;

    @BeforeEach
    void setUp() {
        this.ingredientFactory = new RandomIngredientDTOUnsavedEntityTestFactory(faker, measureUnitFactory);
    }

    @Test
    @DisplayName("createIngredientDTO method should return random IngredientDTO entity-like object")
    void createIngredientDTO_shouldReturnRandomIngredientDTOUnsavedEntity() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomDouble(anyInt(), anyInt(), anyInt())).thenReturn(0d);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple ingredient name");
        when(measureUnitFactory.createMeasureUnitDTO()).thenReturn(new MeasureUnitDTO());

        final IngredientDTO ingredientDTO = ingredientFactory.createIngredientDTO();
        final Long id = ingredientDTO.getId();
        final String name = ingredientDTO.getName();
        final Double amount = ingredientDTO.getAmount();
        final MeasureUnitDTO measureUnitDTO = ingredientDTO.getMeasureUnitDTO();
        final Long version = ingredientDTO.getVersion();

        assertNull(id);
        assertNotNull(name);
        assertNotNull(amount);
        assertNotNull(measureUnitDTO);
        assertNull(version);
    }
}