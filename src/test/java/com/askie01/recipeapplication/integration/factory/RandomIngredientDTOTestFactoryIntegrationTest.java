package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.RandomIngredientDTOTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.IngredientDTOTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RandomIngredientDTOTestFactoryDefaultTestConfiguration.class,
        RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomIngredientDTOTestFactory integration tests")
class RandomIngredientDTOTestFactoryIntegrationTest {

    private final IngredientDTOTestFactory factory;

    @Test
    @DisplayName("createIngredientDTO method should return random IngredientDTO object")
    void createIngredientDTO_shouldReturnRandomIngredientDTO() {
        final IngredientDTO ingredientDTO = factory.createIngredientDTO();
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