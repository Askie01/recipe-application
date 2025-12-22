package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomIngredientDTOUnsavedEntityTestFactoryTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomMeasureUnitDTOUnsavedEntityTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOUnsavedEntityTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        FakerTestConfiguration.class,
        RandomIngredientDTOUnsavedEntityTestFactoryTestConfiguration.class,
        RandomMeasureUnitDTOUnsavedEntityTestFactoryDefaultTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RandomIngredientDTOUnsavedEntityTestFactory integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RandomIngredientDTOUnsavedEntityTestFactoryIntegrationTest {

    private final IngredientDTOUnsavedEntityTestFactory factory;

    @Test
    @DisplayName("createIngredientDTO method should return random IngredientDTO entity-like object")
    void createIngredientDTO_shouldReturnRandomIngredientDTOUnsavedEntity() {
        final IngredientDTO ingredientDTO = factory.createIngredientDTO();
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