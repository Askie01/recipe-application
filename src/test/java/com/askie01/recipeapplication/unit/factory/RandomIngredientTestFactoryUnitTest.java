package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.factory.IngredientTestFactory;
import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.factory.RandomIngredientTestFactory;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.github.javafaker.DateAndTime;
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

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomIngredientTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomIngredientTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;

    @Mock
    private DateAndTime fakerDateAndTime;

    @Mock
    private Date fakerBirthday;

    @Mock
    private MeasureUnitTestFactory measureUnitTestFactory;
    private IngredientTestFactory ingredientTestFactory;

    @BeforeEach
    void setUp() {
        this.ingredientTestFactory = new RandomIngredientTestFactory(faker, measureUnitTestFactory);
    }

    @Test
    @DisplayName("createIngredient method should return random Ingredient object")
    void createIngredient_shouldReturnRandomIngredient() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), eq(false))).thenReturn(0L);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple ingredient name");
        when(faker.date()).thenReturn(fakerDateAndTime);
        when(fakerDateAndTime.birthday()).thenReturn(fakerBirthday);
        when(fakerBirthday.toInstant()).thenReturn(new Date().toInstant());
        when(measureUnitTestFactory.createMeasureUnit()).thenReturn(new MeasureUnit());

        final Ingredient ingredient = ingredientTestFactory.createIngredient();
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