package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.FakerTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomIngredientTestFactoryTestConfiguration;
import com.askie01.recipeapplication.configuration.RandomMeasureUnitTestFactoryTestConfiguration;
import com.askie01.recipeapplication.factory.IngredientTestFactory;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        FakerTestConfiguration.class,
        RandomIngredientTestFactoryTestConfiguration.class,
        RandomMeasureUnitTestFactoryTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomIngredientTestFactory integration tests")
class RandomIngredientTestFactoryIntegrationTest {

    private final IngredientTestFactory factory;

    @Test
    @DisplayName("createIngredient method should return random Ingredient object")
    void createIngredient_shouldReturnRandomIngredient() {
        final Ingredient ingredient = factory.createIngredient();
        final Long ingredientId = ingredient.getId();
        final String ingredientName = ingredient.getName();
        final Double ingredientAmount = ingredient.getAmount();
        final MeasureUnit ingredientMeasureUnit = ingredient.getMeasureUnit();
        final LocalDateTime ingredientCreatedAt = ingredient.getCreatedAt();
        final String ingredientCreatedBy = ingredient.getCreatedBy();
        final LocalDateTime ingredientUpdatedAt = ingredient.getUpdatedAt();
        final String ingredientUpdatedBy = ingredient.getUpdatedBy();
        final Long ingredientVersion = ingredient.getVersion();

        assertNotNull(ingredient);
        assertNotNull(ingredientId);
        assertNotNull(ingredientName);
        assertNotNull(ingredientAmount);
        assertNotNull(ingredientMeasureUnit);
        assertNotNull(ingredientCreatedAt);
        assertNotNull(ingredientCreatedBy);
        assertNotNull(ingredientUpdatedAt);
        assertNotNull(ingredientUpdatedBy);
        assertNotNull(ingredientVersion);
    }
}
